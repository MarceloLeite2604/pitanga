import { Grid } from '@material-ui/core';
import { FC } from 'react';
import { useUpdateState } from '../../../../hooks';
import { Data } from '../../../../Model';
import { Attendees } from './Attendees';
import { EffortValueChart } from './EffortValueChart/EffortValueChart';
import { useEventsCallback } from './hooks/useEventsCallback';

interface Params {
  data: Data
}

export const Room: FC<Params> = ({ data }) => {

  useUpdateState([data.room]);
  useEventsCallback(data);

  return (
    <Grid
      container
      direction='row'>
      <Grid
        item
        xs={12}>
        <EffortValueChart data={data} />
      </Grid>
      <Grid
        item
        xs={12}>
        <>
          {
            data.user &&
            data.room &&
            <Attendees
              user={data.user}
              attendees={data.room.attendees} />
          }
        </>
      </Grid>
    </Grid>
  );
};
