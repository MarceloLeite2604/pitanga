import { useCallback } from 'react';
import { Room, VotingStatus } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useRevealVotesCallback = ([, setData]: CallbackHookParams) => {
  return useCallback(() => {
    setData(previousState => {
      return {
        ...previousState,
        room: {
          ...previousState.room,
          votingStatus: VotingStatus.Closed
        } as Room
      };
    });
  }, []);
};
