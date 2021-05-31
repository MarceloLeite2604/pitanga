import { Attendee as AttendeeModel, Room, User, VotingStatus } from '../../../../../../Model';
import { Card, CardContent, Grid, Typography } from '@material-ui/core';
import { useStyles, Slider } from './components';
import { FC, useMemo } from 'react';

interface Props {
  user: User,
  room: Room,
  attendee: AttendeeModel
}

export const Attendee: FC<Props> = ({ user, room, attendee }) => {

  const styles = useStyles();

  const hideSlider = useMemo(() =>
    (attendee.user.id !== user.id &&
      room.votingStatus === VotingStatus.Open) ||
    !attendee.vote,
  [attendee, room.votingStatus]);

  return (
    <Card
      className={`${styles.attendeeCard} ${user.id === attendee.user.id && styles.userAttendeeCard}`} >
      <CardContent className={styles.attendeeCardContent}>
        <Grid
          container>
          <Grid
            container
            item
            alignContent='center'
            justify='center'
            xs={2}>
            <Typography
              align='left'
              className={styles.iconTypography} >
              {attendee.icon}
            </Typography>
          </Grid>
          <Grid
            container
            item
            xs={10}>
            <Slider
              label='Effort'
              hide={hideSlider}
              value={attendee.vote?.effort || 0} />
            <Slider
              label='Value'
              hide={hideSlider}
              value={attendee.vote?.value || 0} />
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};
