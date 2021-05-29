import { makeStyles, createStyles, lighten } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => createStyles({
  attendeeCard: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center'
  },
  attendeeCardContent: {
    padding: '4px',
    '&:last-child': {
      paddingBottom: '4px'
    }
  },
  userAttendeeCard: {
    background: lighten(theme.palette.background.paper, 0.2)
  },
  iconTypography: {
    fontSize: '1.5rem'
  }
}));
