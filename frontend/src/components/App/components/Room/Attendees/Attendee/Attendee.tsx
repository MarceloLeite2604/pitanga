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
            <Slider label='Effort' value={4} />
            <Slider label='Value' value={2} />
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};
