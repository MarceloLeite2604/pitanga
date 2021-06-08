import { useCallback } from 'react';
import { UserCreatedEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useUserCreatedCallback = ([, setData]: CallbackHookParams) => {
  return useCallback((event: UserCreatedEvent) => {
    if (event.payload) {
      setData(previousState => {
        return {
          ...previousState,
          user: event.payload?.user
        };
      });
    }
  }, []);
};
