import { makeStyles, createStyles, lighten } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => createStyles({
  attendeeCard: {
    minHeight: '6rem',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center'
  },
  userAttendeeCard: {
    background: lighten(theme.palette.background.paper, 0.2)
  },
  iconTypography: {
    fontSize: '2rem'
  }
}));
