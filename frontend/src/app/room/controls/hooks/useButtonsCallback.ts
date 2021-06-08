import { useCallback } from 'react';
import { buildResetRoomEvent, buildRevealVotesEvent, Data } from '../../../../shared/model';

interface Return {
  onResetButtonClick: () => void,
  onRevealButtonClick: () => void
}

type Hook = (data: Data) => Return;

export const useButtonsCallback: Hook = (data) => {
  const onResetButtonClick = useCallback(() => {
    data.subjects.$out.next(buildResetRoomEvent());
  }, [data]);

  const onRevealButtonClick = useCallback(() => {
    data.subjects.$out.next(buildRevealVotesEvent());
  }, [data]);

  return {
    onResetButtonClick,
    onRevealButtonClick
  };
};
