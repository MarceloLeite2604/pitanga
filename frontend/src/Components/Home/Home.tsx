import { FC, useState } from 'react';
import { PitangaWebSocket } from '../../Hooks';
import { buildCreateRoomEvent, User } from '../../Model';

interface Props {
  connected: boolean,
  user?: User,
  pitangaWebSocket: PitangaWebSocket
}

export const Home: FC<Props> = ({ connected, user, pitangaWebSocket }) => {

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [roomNumber, setRoomNumber] = useState<string>();

  const sendCreateRoomEvent = () => {
    user && pitangaWebSocket.$outgoingEvent.next(buildCreateRoomEvent(user));
  };

  return (
    <>
      <div>
        <label htmlFor='roomNumber'>Room #:</label>
        <input id='roomNumber' type='text' maxLength={8} onChange={ event => setRoomNumber(event.target.value)}></input>
        <button
          disabled={!connected || !user}>Join Room</button>
      </div>
      <div><p>Or</p></div>
      <div>
        <button
          disabled={!connected || !user}
          onClick={sendCreateRoomEvent}>Create room</button>
      </div>
    </>
  );
};
