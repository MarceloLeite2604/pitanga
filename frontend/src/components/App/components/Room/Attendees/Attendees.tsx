import { useUpdateState } from '../../../../../hooks';
import { sortByJoinedAtWithCurrentUserFirst, User, Attendee as AttendeeModel } from '../../../../../Model';
import { Grid } from '@material-ui/core';
import { FC } from 'react';
import { Attendee } from './Attendee';

interface Props {
  attendees: AttendeeModel[],
  user: User
}

export const Attendees: FC<Props> = ({ attendees, user }) => {

  useUpdateState([attendees, user]);

  return (
    <Grid
      container
      alignItems='flex-start'
      justify='flex-start'
      spacing={1} >
      {
        sortByJoinedAtWithCurrentUserFirst(attendees, user)
          .map((attendee, index) =>
            <Grid
              item
              key={`attendee-grid-${index}`}
              md={6}
              xs={12}>
              <Attendee
                user={user}
                attendee={attendee} />
            </Grid>)
      }
    </Grid >
  );
};
