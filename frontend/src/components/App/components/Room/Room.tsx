import { Grid } from '@material-ui/core';
import { FC, useMemo } from 'react';
import { useUpdateState } from '../../../../hooks';
import { Data, retrieveAttendeeForUser } from '../../../../Model';
import { Attendees, EffortValueChart, Controls } from './components';
import { useEventsCallback } from './hooks';

interface Params {
  data: Data
}

export const Room: FC<Params> = ({ data }) => {

  useUpdateState([data.room]);
  useEventsCallback(data);

  const roomOwner = useMemo(() => {
    console.log('Retrieving room owner.');
    if (data.room?.attendees && data.user) {
      const userAttendee = retrieveAttendeeForUser(data.room?.attendees, data.user);
      console.log(`Attendee is ${JSON.stringify(userAttendee)}.`);
      return userAttendee?.roomOwner || false;
    }
    return false;
  }, [data]);

  return (
    <Grid
      container
      direction='row'
      spacing={1}>
      <Grid
        item
        xs={9}>
        <EffortValueChart data={data} />
      </Grid>
      <Grid
        item
        xs={3}>
        <>
          {
            data.user &&
            data.room &&
            <Attendees
              user={data.user}
              room={data.room} />
          }
        </>
      </Grid>
      <Grid
        item
        xs={12}>
        {roomOwner && <Controls data={data} />}
      </Grid>
    </Grid>
  );
};
