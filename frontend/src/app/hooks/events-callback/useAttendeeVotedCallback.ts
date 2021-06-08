import { useCallback } from 'react';
import { AttendeeVotedEvent, removeUserFromAttendees, Room } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useAttendeeVotedCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: AttendeeVotedEvent) => {
    if (data.room?.attendees && event.payload) {
      const attendees = [event.payload.attendee, ...removeUserFromAttendees(event.payload.attendee.user, data.room?.attendees)];
      setData(previousState => {
        return {
          ...previousState,
          room: {
            ...previousState.room,
            votingStatus: event.payload?.votingStatus,
            attendees
          } as Room
        };
      });
    }
  }, [data.room]);
};
