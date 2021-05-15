import { useEffect, useState } from 'react';
import { usePitangaWebSocket } from '../Hooks';
import { Room, User } from '../Model';
import {
  buildJoinUserEvent,
  IncomingEventType
} from '../Model/Events';
import { Home } from './Home';
import { Switch, Route } from 'react-router-dom';

export function App() {
  const [connected, setConnected] = useState(false);
  const [room, setRoom] = useState<Room>();
  const [user, setUser] = useState<User>();
  const pitangaWebSocket = usePitangaWebSocket();

  useEffect(() => {
    pitangaWebSocket.$connected.subscribe(connected => {
      if (connected && !user) {
        pitangaWebSocket.$outgoingEvent.next(buildJoinUserEvent());
      }
      setConnected(connected);
    });
    pitangaWebSocket.$incomingEvent.subscribe(incomingEvent => {
      console.log(`Event type: ${incomingEvent.type}`);
      switch (incomingEvent.type) {
        case IncomingEventType.RoomCreated:
          setRoom(incomingEvent.payload as Room);
          break;
        case IncomingEventType.UserJoined:
          setUser(incomingEvent.payload as User);
          break;
      }
    });
  }, []);

  return (
    <Switch>
      <Route path='/:roomId'>
        <div>
          <p>Welcome to the room.</p>
        </div>
      </Route>
      <Route path='/'>
        {!room &&
          <Home
            connected={connected}
            user={user}
            pitangaWebSocket={pitangaWebSocket}
            setRoom={setRoom} />}
      </Route>
    </Switch>);
}
