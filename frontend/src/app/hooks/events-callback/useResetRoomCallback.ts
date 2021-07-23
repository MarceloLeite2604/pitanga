import { useCallback } from 'react';
import { createRoom, ResetRoomEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useResetRoomCallback = ([, setData]: CallbackHookParams) => {
  return useCallback((event: ResetRoomEvent) => {
    if (event.payload) {
      const room = createRoom(event.payload.room);
      setData(previousState => {
        return {
          ...previousState,
          room
        };
      });
    }
  }, []);
};
