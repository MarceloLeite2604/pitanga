import { useCallback } from 'react';
import { useHistory } from 'react-router-dom';
import { RoomCreatedEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useRoomCreatedCallback = ([, setData]: CallbackHookParams) => {
  const history = useHistory();
  return useCallback((event: RoomCreatedEvent) => {
    if (event.payload) {
      setData(previousState => {
        return {
          ...previousState,
          room: event.payload?.room,
          attendee: event.payload?.room.attendees[0]
        };
      });
      history.push(`/${event.payload.room.id}`);
    }
  }, [history]);
};
