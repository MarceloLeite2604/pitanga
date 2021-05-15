import { Dispatch, FC, SetStateAction, useEffect, useState } from 'react';
import { Subscription } from 'rxjs';
import { PitangaWebSocket } from '../../Hooks';
import { buildCreateRoomEvent, IncomingEventType, Room, User } from '../../Model';
import { useHistory } from 'react-router-dom';

interface Props {
  connected: boolean,
  user?: User,
  pitangaWebSocket: PitangaWebSocket
  setRoom: Dispatch<SetStateAction<Room | undefined>>
}

export const Home: FC<Props> = ({ connected, user, pitangaWebSocket, setRoom }) => {

  const [incomingEventSubscription, setIncomingEventSubscription] = useState<Subscription>();
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [roomNumber, setRoomNumber] = useState<string>();
  const history = useHistory();

  useEffect(() => {
    setIncomingEventSubscription(pitangaWebSocket.$incomingEvent.subscribe(incomingEvent => {
      if (incomingEvent.type === IncomingEventType.RoomCreated) {
        const room = incomingEvent.payload as Room;
        setRoom(room);
        history.push(`/${room.id}`);
      }
    }));
    return () => {
      incomingEventSubscription?.unsubscribe();
    };
  }, [setRoom, history]);

  const sendCreateRoomEvent = () => {
    user && pitangaWebSocket.$outgoingEvent.next(buildCreateRoomEvent(user));
  };

  return (
    <>
      <div>
        <label htmlFor='roomNumber'>Room #:</label>
        <input id='roomNumber' type='text' maxLength={8} onChange={ event => setRoomNumber(event.target.value)}></input>
        <button
          disabled={!connected}>Join Room</button>
      </div>
      <div><p>Or</p></div>
      <div>
        <button
          disabled={!connected}
          onClick={sendCreateRoomEvent}>Create room</button>
      </div>
    </>
  );
};
