import { Grid } from '@material-ui/core';
import { useUpdateState } from '../../../shared/hooks';
import { sortByJoinedAtWithCurrentUserFirst, User, Room } from '../../../shared/model';
import { Attendee } from './attendee';

interface Props {
  room: Room,
  user: User
}

export const Attendees = ({ room, user }: Props) => {

  useUpdateState([room, user]);

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
