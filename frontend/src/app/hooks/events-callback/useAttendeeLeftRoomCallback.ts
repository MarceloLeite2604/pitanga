import { useCallback } from 'react';
import { AttendeeLeftRoomEvent, removeAttendeeFromAttendees, removeUserFromAttendees, Room, Toast, ToastType } from '../../../shared/model';
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
          let remainingAttendees = [...removeAttendeeFromAttendees(event.payload?.attendee, data.room?.attendees)];
          if (event.payload.newRoomOwner) {
            remainingAttendees = [...removeUserFromAttendees(event.payload.newRoomOwner.user, remainingAttendees), event.payload.newRoomOwner];
            const toast : Toast = {
              message: `${event.payload.attendee.icon} left. New room owner is ${event.payload.newRoomOwner.icon}.`,
              type: ToastType.INFORMATION
            };
            data.subjects.$toast.next(toast);
          }
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
      }
    }
  }, [data.attendee, data.room]);
};
