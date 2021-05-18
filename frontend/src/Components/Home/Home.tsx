import { FC, useState } from 'react';
import { PitangaWebSocket } from '../../Hooks';
import { buildCreateRoomEvent, User } from '../../Model';
// import { useHistory } from 'react-router-dom';

interface Props {
  connected: boolean,
  user?: User,
  pitangaWebSocket: PitangaWebSocket
}

export const Home: FC<Props> = ({ connected, user, pitangaWebSocket }) => {

  // const [incomingEventSubscription, setIncomingEventSubscription] = useState<Subscription>();
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [roomNumber, setRoomNumber] = useState<string>();
  // const history = useHistory();

  // useEffect(() => {
  //   setIncomingEventSubscription(pitangaWebSocket.$incomingEvent.subscribe(incomingEvent => {
  //     if (incomingEvent.type === EventType.RoomCreated) {
  //       const room = incomingEvent.payload as Room;
  //       setRoom(room);
  //       history.push(`/${room.id}`);
  //     }
  //   }));
  //   return () => {
  //     incomingEventSubscription?.unsubscribe();
  //   };
  // }, [setRoom, history]);

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
