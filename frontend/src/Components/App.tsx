import { useCallback, useEffect, useState } from 'react';
import { Switch, Route, useHistory } from 'react-router-dom';
import { Subscription } from 'rxjs';

import {
  Room,
  User,
  EventType,
  Event,
  CheckRoomExistsPayload,
  UserJoinedPayload
} from '../Model';
import { Home } from './Home';
import { usePitangaWebSocket } from '../Hooks';
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
    console.log(`Received event "${incomingEvent.type}"`);
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
        if (!payload.exists) {
          setRoom(undefined);
          history.push('/');
        } else {
          if (!room) {
            setRoom(payload.room);
          }
        }
        break;
      }
      case EventType.MaxUsersReached: {
        pitangaWebSocket.$connected.next(false);
        break;
      }
      case EventType.UserJoined: {
        const payload = incomingEvent.payload as UserJoinedPayload;
        if (room && room.id === payload.room.id) {
          if (!room.users.includes(payload.user)) {
            room.users.push(payload.user);
            setRoom(room);
          }
        }
        break;
      }
      default: {
        console.log(`[ERROR] Don't know how to handle "${incomingEvent.type}" event.`);
      }
    }
  }, [history, room]);

  const connectionCallback = useCallback((connected: boolean) => {
    setConnected(connected);
  }, []);

  useEffect(() => {
    eventsSubscription?.unsubscribe();
    connectionSubscription?.unsubscribe();

    setEventsSubscription(pitangaWebSocket.$incomingEvent.subscribe(eventsCallback));
    setConnectionSubscription(pitangaWebSocket.$connected.subscribe(connectionCallback));

    return () => {
      connectionSubscription?.unsubscribe();
      eventsSubscription?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);

  return (
    <Switch>
      <Route path='/:roomId'>
        <RoomPage
          connected={connected}
          user={user}
          room={room}
          pitangaWebSocket={pitangaWebSocket} />
      </Route>
      <Route path='/'>
        <Home
          connected={connected}
          user={user}
          pitangaWebSocket={pitangaWebSocket} />
      </Route>
    </Switch>);
}
