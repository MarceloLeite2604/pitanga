import { useCallback } from 'react';
import { ResetRoomEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useResetRoomCallback = ([, setData]: CallbackHookParams) => {
  return useCallback((event: ResetRoomEvent) => {
    if (event.payload) {
      setData(previousState => {
        return {
          ...previousState,
          room: event.payload?.room
        };
      });
    }
  }, []);
};
