import { makeStyles, createStyles, lighten } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => createStyles({
  attendeeCard: {
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center'
  },
  attendeeCardContent: {
    padding: '4px',
    minHeight: '48px',
    '&:last-child': {
      paddingBottom: '4px'
    }
  },
  currentUser: {
    background: theme.palette.primary.dark
  },
  currentUserVoted: {
    background: lighten(theme.palette.primary.dark, 0.2)
  },
  otherUserVoted: {
    background: lighten(theme.palette.background.paper, 0.2)
  },
  iconTypography: {
    fontSize: '1.5rem'
  }
}));
