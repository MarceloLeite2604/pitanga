import { Attendee as AttendeeModel, User } from '../../../../../../Model';
import { Card, CardContent, Grid, Typography } from '@material-ui/core';
import { useStyles, Slider } from './components';

interface Props {
  user: User,
  attendee: AttendeeModel
}

export const Attendee = ({ user, attendee }: Props) => {

  const styles = useStyles();

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
              hide={!attendee.vote}
              value={attendee.vote?.effort || 0} />
            <Slider
              label='Value'
              hide={!attendee.vote}
              value={attendee.vote?.value || 0} />
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};
