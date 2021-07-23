import { useCallback } from 'react';
import { useHistory } from 'react-router';
import { CheckRoomExistsEvent, createRoom } from '../../../shared/model';
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
        const room = createRoom(event.payload.room);
        setData(previousState => {
          return {
            ...previousState,
            room
          };
        });
      }
    }
  }, [history, data.room]);
};
