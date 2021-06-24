import { useMemo } from 'react';
import { Card, CardContent, Grid, Typography } from '@material-ui/core';
import { Attendee as AttendeeModel, Room, User, VotingStatus } from '../../../../shared/model';
import { useUpdateState } from '../../../../shared/hooks';
import { Slider } from './slider';
import { useStyles } from './hooks';

interface Props {
  user: User,
  room: Room,
  attendee: AttendeeModel
}

export const Attendee = ({ user, room, attendee }: Props) => {

  const styles = useStyles();

  useUpdateState([user, room, attendee]);

  const hideSlider = useMemo(() =>
    (attendee.user.id !== user.id &&
      room.votingStatus === VotingStatus.Open) ||
    !attendee.vote,
  [attendee, room.votingStatus]);

  let cardClassName = styles.attendeeCard;

  if (user.id === attendee.user.id) {
    cardClassName += ` ${attendee.vote ? styles.currentUserVoted : styles.currentUser}`;
  } else if (attendee.vote) {
    cardClassName += ` ${styles.otherUserVoted}`;
  }

  return (
    <Card
      className={cardClassName} >
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
