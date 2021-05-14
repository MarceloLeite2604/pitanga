import { useEffect, useState } from 'react';
import { usePitangaWebSocket } from '../Hooks';
import { Room, User } from '../Model';
import {
  buildCreateRoomEvent,
  buildJoinUserEvent,
  IncomingEventType
} from '../Model/Events';

export function App() {
  const [connected, setConnected] = useState(false);
  const { $connected, $outgoingEvent, $incomingEvent } = usePitangaWebSocket();
  const [room, setRoom] = useState<Room>();
  const [user, setUser] = useState<User>();

  useEffect(() => {
    $connected.subscribe(connected => {
      if (connected && !user) {
        $outgoingEvent.next(buildJoinUserEvent());
      }
      setConnected(connected);
    });
    $incomingEvent.subscribe(incomingEvent => {
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

  const sendMessage = () => {
    user && $outgoingEvent.next(buildCreateRoomEvent(user));
  };

  return (
    <div>
      <p>Connected? {String(connected)}</p>
      <button
        disabled={!connected}
        onClick={sendMessage}>Create room</button>
      {user && <p>User id: {user.id}</p>}
      {room && <p>Room id: {room.id}</p>}
    </div >
  );
}
