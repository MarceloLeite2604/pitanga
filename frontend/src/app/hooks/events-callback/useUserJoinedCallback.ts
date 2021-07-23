import { useCallback } from 'react';
import {
  createAttendee,
  createRoom,
  UserJoinedEvent
} from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useUserJoinedCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: UserJoinedEvent) => {
    if (data.room && event.payload && data.room.id === event.payload.room.id) {
      setData(previousState => {
        return {
          ...previousState,
          room: createRoom(event.payload?.room)
        };
      });
      if (!data.attendee) {
        const roomAttendee = event.payload.room.attendees.find(att => att?.user.id === data.user?.id);
        if (roomAttendee) {
          setData(previousState => {
            return {
              ...previousState,
              attendee: createAttendee(roomAttendee)
            };
          });
        }
      }
    }
  }, [data.room, data.attendee, data.user]);
};
