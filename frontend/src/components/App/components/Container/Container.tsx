import { Grid } from '@material-ui/core';
import { FC } from 'react';
import { BoxMargin } from './BoxMargin';
import { useStyles } from './ContainerStyle';

export const Container: FC = ({ children }) => {

  const styles = useStyles();

  return <Grid
    container
    alignItems='center'
    justify='center'
    spacing={0}
    className={styles.container} >
    <BoxMargin />
    <Grid item
      md={10}
      xs={12}>
      {children}
    </Grid>
    <BoxMargin />
  </Grid >;
};
