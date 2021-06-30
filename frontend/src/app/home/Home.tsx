import { useCallback, useState } from 'react';
import { useHistory } from 'react-router';
import { Button, TextField, Grid, Typography } from '@material-ui/core';
import { buildCreateRoomEvent, Data } from '../../shared/model';
import { GridItem } from './GridItem';
import { useStyles } from './hooks';

interface Params {
  data: Data
}

export const Home = ({ data }: Params) => {

  const { connected, user, subjects } = data;
  const [roomNumber, setRoomNumber] = useState<string>();

  const history = useHistory();
  
  const joinRoom = useCallback(() => {
    history.push(`${roomNumber}`);
  }, [history, roomNumber]);

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

  const classes = useStyles();

  const joinRoomButtonDisabled = !connected || !user || !validateRoomNumber(roomNumber);

  return (
    <Grid
      container
      spacing={2}
      alignItems='center'
      style={{ flexGrow: 0 }}>
      <GridItem
        xs={6}
        alignItems='flex-end'>
        <TextField
          id='roomNumber'
          label='Room number'
          inputProps={{
            maxLength: '8'
          }}
          onChange={event => setRoomNumber(event.target.value)} />
      </GridItem>
      <GridItem
        xs={6}
        alignItems='flex-start'>
        <Button
          variant='contained'
          color='primary'
          disabled={joinRoomButtonDisabled}
          onClick={joinRoom}
          className={classes.joinRooomButton}>Join Room</Button>
      </GridItem>
      <GridItem xs={12}>
        <Typography>Or</Typography>
      </GridItem>
      <GridItem xs={12}>
        <Button
          variant='contained'
          color='primary'
          disabled={!connected || !user}
          onClick={sendCreateRoomEvent}>Create room</Button>
      </GridItem>
    </Grid >
  );
};
