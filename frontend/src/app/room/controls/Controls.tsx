import { Button, Grid } from '@material-ui/core';
import { useMemo } from 'react';
import { Data, VotingStatus } from '../../../shared/model';
import { useButtonsCallback } from './hooks';

interface Props {
  data: Data
}

export const Controls = ({ data }: Props) => {

  const { onResetButtonClick, onRevealButtonClick } = useButtonsCallback(data);

  const noVotes = useMemo(() => {
    console.log(`Attendees: ${JSON.stringify(data.room?.attendees)}`);
    if (!data.room?.attendees) {
      return false;
    }

    debugger;
    return data.room
      .attendees
      .filter(attendee => attendee.vote)
      .length === 0;
  }, [data.room?.attendees]);

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
          onClick={onResetButtonClick}>
          Reset
        </Button>
      </Grid>
      <Grid
        item
        xs={2}>
        <Button
          variant='contained'
          disabled={noVotes || data.room?.votingStatus === VotingStatus.Closed}
          onClick={onRevealButtonClick}>
          Reveal
        </Button>
      </Grid>
    </Grid>
  );
};
