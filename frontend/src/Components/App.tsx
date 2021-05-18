import { useCallback, useEffect, useState } from 'react';
import { Switch, Route, useHistory } from 'react-router-dom';

import {
  Room,
  User,
  buildCreateUserEvent,
  EventType,
  Event,
  CheckRoomExistsPayload
} from '../Model';
import { Home } from './Home';
import { usePitangaWebSocket } from '../Hooks';
import { Subscription } from 'rxjs';
import { Room as RoomPage } from './Room';

export function App() {
  const [connected, setConnected] = useState(false);
  const [room, setRoom] = useState<Room>();
  const [user, setUser] = useState<User>();
  const pitangaWebSocket = usePitangaWebSocket();
  const history = useHistory();
  const [connectionSubscription, setConnectionSubscription] = useState<Subscription>();
  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();

  const eventsCallback = useCallback((incomingEvent: Event<any>) => {
    switch (incomingEvent.type) {
      case EventType.RoomCreated: {
        const receivedRoom = incomingEvent.payload as Room;
        setRoom(receivedRoom);
        history.push(`/${receivedRoom.id}`);
        break;
      }
      case EventType.UserCreated: {
        setUser(incomingEvent.payload as User);
        break;
      }
      case EventType.CheckRoomExists: {
        const payload = incomingEvent.payload as CheckRoomExistsPayload;
        debugger;
        if (!payload.exists) {
          setRoom(undefined);
          history.push('/');
        }
        break;
      }
      default: {
        console.log(`[ERROR] Don't know how to handle "${incomingEvent.type}" event.`);
      }
    }
  }, [history, setRoom, setUser]);

  const connectionCallback = useCallback((connected: boolean) => {
    if (connected && !user) {
      pitangaWebSocket.$outgoingEvent.next(buildCreateUserEvent());
    }
    setConnected(connected);
  }, [pitangaWebSocket, user, setConnected]);

  useEffect(() => {
    setConnectionSubscription(pitangaWebSocket.$connected.subscribe(connectionCallback));
    setEventsSubscription(pitangaWebSocket.$incomingEvent.subscribe(eventsCallback));
    return () => {
      connectionSubscription?.unsubscribe();
      eventsSubscription?.unsubscribe();
    };
  }, []);

  return (
    <Switch>
      <Route path='/:roomId'>
        <RoomPage
          user={user}
          room={room}
          pitangaWebSocket={pitangaWebSocket} />
      </Route>
      <Route path='/'>
        {!room &&
          <Home
            connected={connected}
            user={user}
            pitangaWebSocket={pitangaWebSocket} />}
      </Route>
    </Switch>);
}
