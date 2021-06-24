import { useCallback } from 'react';
import { useHistory } from 'react-router';
import { CheckRoomExistsEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useCheckRoomExistsCallback = ([data, setData]: CallbackHookParams) => {
  const history = useHistory();
  return useCallback((event: CheckRoomExistsEvent) => {
    if (event.payload) {
      if (!event.payload.exists) {
        setData(previousState => {
          return {
            ...previousState,
            room: undefined
          };
        });
        history.push('/');
      } else {
        setData(previousState => {
          return {
            ...previousState,
            room: event.payload?.room
          };
        });
      }
    }
  }, [history, data.room]);
};
