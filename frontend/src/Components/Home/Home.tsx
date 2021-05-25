import { FC, useCallback, useState } from 'react';
import { useHistory } from 'react-router';
import { Button, TextField, Grid } from '@material-ui/core';
import { buildCreateRoomEvent, Data } from '../../Model';

interface Params {
  data: Data
}

export const Home: FC<Params> = ({ data }) => {

  const { connected, user, subjects } = data;
  const [roomNumber, setRoomNumber] = useState<string>();

  const history = useHistory();
  const joinRoom = useCallback(() => {
    history.push(`/${roomNumber}`);
  }, [history]);

  const sendCreateRoomEvent = () => {
    user && subjects.$out.next(buildCreateRoomEvent(user));
  };

  const validateRoomNumber = useCallback((roomNum?: string) => {

    if (!roomNum) {
      return false;
    }

    if (roomNum.length !== 8) {
      return false;
    }

    if (isNaN(parseInt(roomNum))) {
      return false;
    }

    return true;
  }, []);

  const joinRoomButtonDisabled = !connected || !user || !validateRoomNumber(roomNumber);

  return (
    <Grid
      container
      spacing={2}>
      <Grid
        item
        xs={6}>
        <TextField
          id='roomNumber'
          label='Room number'
          inputProps={{
            maxLength: '8'
          }}
          onChange={event => setRoomNumber(event.target.value)} />
      </Grid>
      <Grid
        item
        xs={6}>
        <Button
          variant='contained'
          color='primary'
          disabled={joinRoomButtonDisabled}
          onClick={joinRoom}>Join Room</Button>
      </Grid>
      <Grid
        item
        xs={12}>
        <p>Or</p>
      </Grid>
      <Grid
        item
        xs={12}>
        <Button
          variant='contained'
          color='primary'
          disabled={!connected || !user}
          onClick={sendCreateRoomEvent}>Create room</Button>
      </Grid>
    </Grid >
  );
};
