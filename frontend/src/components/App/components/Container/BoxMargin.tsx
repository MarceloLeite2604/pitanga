import { Box, Grid } from '@material-ui/core';

export const BoxMargin = () =>
  <Box
    component={Grid}
    display={{
      md: 'block',
      xs: 'none'
    }} />;
