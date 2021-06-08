import { useCallback } from 'react';
import { CallbackHookParams } from '../types';

export const useMaxUsersReachedCallback = ([data]: CallbackHookParams) => {
  return useCallback(() => {
    data.subjects.$connected.next(false);
  }, []);
};
