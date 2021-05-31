import { useUpdateState } from '../../../../../hooks';
import { sortByJoinedAtWithCurrentUserFirst, User, Room } from '../../../../../Model';
import { Grid } from '@material-ui/core';
import { FC } from 'react';
import { Attendee } from './Attendee';

interface Props {
  room: Room,
  user: User
}

export const Attendees: FC<Props> = ({ room, user }) => {

  useUpdateState([room.attendees, user]);

  return (
    <Grid
      container
      alignItems='flex-start'
      justify='flex-start'
      spacing={1} >
      {
        sortByJoinedAtWithCurrentUserFirst(room.attendees, user)
          .map((attendee, index) =>
            <Grid
              item
              key={`attendee-grid-${index}`}
              md={6}
              xs={12}>
              <Attendee
                user={user}
                room={room}
                attendee={attendee} />
            </Grid>)
      }
    </Grid >
  );
};
