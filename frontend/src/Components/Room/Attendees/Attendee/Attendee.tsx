import { Attendee as AttendeeModel, User } from '../../../../Model';
import { Card, CardContent, Typography } from '@material-ui/core';
import { useStyles } from './AtendeeStyles';

interface Props {
  user: User,
  attendee: AttendeeModel
}

export const Attendee = ({ user, attendee }: Props) => {

  const classes = useStyles();

  return (
    <Card className={`${classes.attendeeCard} ${user.id === attendee.user.id && classes.userAttendeeCard}`} >
      <CardContent>
        <Typography align='center' className={classes.iconTypography} >
          {attendee.icon}
        </Typography>
      </CardContent>
    </Card>
  );
};
