import { useCallback } from 'react';
import {
  AttendeeLeftRoomEvent,
  Toast,
  ToastType,
  createAttendee
} from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useAttendeeLeftRoomCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: AttendeeLeftRoomEvent) => {
    if (event.payload) {
      /* User is the actual attendee. */
      if (data.attendee && data.attendee.user.id === event.payload.attendee.user.id) {
        setData(previousState => {
          return {
            ...previousState,
            room: undefined,
            attendee: undefined
          };
        });
      } else {
        debugger;
        if (data.room?.attendees) {
          let room = data.room.removeAttendeeByUserId(event.payload.attendee.user.id);
          if (event.payload.newRoomOwner) {
            const newRoomOwner = createAttendee(event.payload.newRoomOwner);
            room = room.removeAttendeeByUserId(event.payload.newRoomOwner.user.id);
            room.attendees.push(newRoomOwner);
            const toast: Toast = {
              message: `${event.payload.attendee.icon} left. New room owner is ${event.payload.newRoomOwner.icon}.`,
              type: ToastType.INFORMATION
            };
            data.subjects.$toast.next(toast);
          }
          setData(previousState => {
            return {
              ...previousState,
              room
            };
          });
        }
      }
    }
  }, [data.attendee, data.room]);
};
