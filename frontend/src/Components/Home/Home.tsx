import { useState } from 'react';
import { buildCreateRoomEvent, Data } from '../../Model';

export const Home = (data: Data) => {

  const { connected, user, subjects } = data;

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [roomNumber, setRoomNumber] = useState<string>();

  const sendCreateRoomEvent = () => {
    user && subjects.$out.next(buildCreateRoomEvent(user));
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
