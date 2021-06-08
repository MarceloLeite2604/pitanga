import { useCallback } from 'react';
import { Room, UserDroppedEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useUserDroppedCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: UserDroppedEvent) => {
    if (data.room && event.payload) {
      const remainingAttendees = data.room.attendees
        .filter(attendee => attendee.user.id !== event.payload?.user.id);
      setData(previousState => {
        return {
          ...previousState,
          room: {
            ...previousState.room,
            attendees: remainingAttendees
          } as Room
        };
      });
    }
  }, [data.room]);
};
