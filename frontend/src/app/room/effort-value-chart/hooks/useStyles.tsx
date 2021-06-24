import { createStyles, makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => createStyles({
  responsiveContainer: {
    borderRadius: '10px',
    background: theme.palette.grey[800]
  }
}));
