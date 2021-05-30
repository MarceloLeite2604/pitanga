import { Grid, Slider as MuiSlider, Typography } from '@material-ui/core';
import { FC } from 'react';
import { useUpdateState } from '../../../../../../../../hooks';
import { useStyles } from './SliderStyles';

interface Props {
  label: string,
  value: number,
  hide?: boolean
}

export const Slider: FC<Props> = ({ label, value, hide }) => {
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
      xs={7}>
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
