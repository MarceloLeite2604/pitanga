import { Grid, Slider as MuiSlider, Typography } from '@material-ui/core';
import { useUpdateState } from '../../../../../shared/hooks';
import { useStyles } from './hooks';

interface Props {
  label: string,
  value: number,
  hide?: boolean
}

export const Slider = ({ label, value, hide }: Props) => {
  const styles = useStyles();

  useUpdateState([value, hide]);

  if (hide) {
    return <></>;
  }

  return <Grid
    container
    alignItems='center'
    spacing={2}>
    <Grid
      item
      xs={3}>
      <Typography
        align='right'
        className={styles.typography}>
        {label}:
      </Typography>
    </Grid>
    <Grid
      item
      xs={7}
      className={styles.muiSliderGridItem}>
      <MuiSlider
        key={`${label}-${value}-${String(hide)}`}
        disabled
        marks
        min={0}
        max={6}
        defaultValue={value}
        className={styles.muiSlider} />
    </Grid>
    <Grid
      item
      xs={2}>
      <Typography
        align='left'
        className={styles.typography} >
        {value}
      </Typography>
    </Grid>
  </Grid>;
};
