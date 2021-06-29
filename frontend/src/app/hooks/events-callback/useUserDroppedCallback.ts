import { useCallback } from 'react';
import { Room, Toast, ToastType, UserDroppedEvent } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useUserDroppedCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: UserDroppedEvent) => {
    if (data.room && event.payload) {
      const droppedAttendee = data.room.attendees
        .find(attendee => attendee.user.id === event.payload?.user.id);

      const remainingAttendees = data.room.attendees
        .filter(attendee => attendee !== droppedAttendee);

      const toast: Toast = {
        message: `${droppedAttendee?.icon} dropped.`,
        type: ToastType.INFORMATION
      };

      if (event.payload.newRoomOwner) {
        debugger;
        const newRoomOwner = remainingAttendees.find(attendee => attendee.user.id === event.payload?.newRoomOwner?.user.id);
        if (newRoomOwner) {
          newRoomOwner.roomOwner = true;

          toast.message = `${toast.message} New room owner is ${newRoomOwner?.icon}.`;
        }
      }

      data.subjects.$toast.next(toast);

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
