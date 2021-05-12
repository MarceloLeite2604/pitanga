import { useCallback, useEffect, useState } from 'react';
import { usePitangaWebSocket } from '../Hooks';
import { Room } from '../Model';
import { buildCreateRoomEvent, IncomingEvent, IncomingEventType } from '../Model/Events';

export function App() {

  const [connected, setConnected] = useState(false);
  const { $connected, $outgoingEvent, $incomingEvent } = usePitangaWebSocket();
  const [incomingEvent, setIncomingEvent] = useState<IncomingEvent<Room>>();

  useEffect(() => {
    $connected.subscribe(setConnected);
    $incomingEvent.subscribe(incomingEvent => {
      if (incomingEvent.type === IncomingEventType.RoomCreated) {
        setIncomingEvent(incomingEvent as IncomingEvent<Room>);
      }
    });
  }, []);

  const sendMessage = useCallback(() => {
    console.log('Creating room.');
    $outgoingEvent.next(buildCreateRoomEvent());
  }, [$outgoingEvent]);

  return (
    <div>
      <p>Connected? {String(connected)}</p>
      <button
        disabled={!connected}
        onClick={sendMessage}>Send message</button>
      {incomingEvent?.payload && <p>Room id: {incomingEvent.payload.id}</p>}
    </div >
  );
}
