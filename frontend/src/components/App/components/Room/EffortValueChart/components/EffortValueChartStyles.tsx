import { createStyles, makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => createStyles({
  responsiveContainer: {
    background: theme.palette.background.paper
  }
}));
