import { Button, Grid } from '@material-ui/core';
import { FC } from 'react';
import { Data, VotingStatus } from '../../../../../../Model';
import { useButtonsCallback } from './hooks';

interface Props {
  data: Data
}

export const Controls: FC<Props> = ({ data }) => {

  const { onResetButtonClick, onRevealButtonClick } = useButtonsCallback(data);

  return (
    <Grid
      container>
      <Grid
        item
        xs={2} />
      <Grid
        item
        xs={2}>
        <Button
          variant='contained'
          onClick={onResetButtonClick}>Reset</Button>
      </Grid>
      <Grid
        item
        xs={2}>
        <Button
          variant='contained'
          disabled={data.room?.votingStatus === VotingStatus.Closed}
          onClick={onRevealButtonClick}
        >Reveal</Button>
      </Grid>
    </Grid>
  );
};
