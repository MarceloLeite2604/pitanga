import { FC } from 'react';
import { Grid, GridSize, GridItemsAlignment } from '@material-ui/core';

interface Params {
  xs?: boolean | GridSize,
  alignItems?: GridItemsAlignment
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export const GridItem: FC<Params> = ({ xs, alignItems, children }) => {

  return (
    <Grid
      container
      item
      xs={xs}
      direction='column'
      justify='flex-start'
      alignItems={alignItems || 'center'}>
      {children}
    </Grid>
  );
};
