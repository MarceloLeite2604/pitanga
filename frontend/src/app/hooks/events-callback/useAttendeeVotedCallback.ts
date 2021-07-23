import { useCallback } from 'react';
import { AttendeeVotedEvent, createAttendee } from '../../../shared/model';
import { CallbackHookParams } from '../types';

export const useAttendeeVotedCallback = ([data, setData]: CallbackHookParams) => {
  return useCallback((event: AttendeeVotedEvent) => {
    if (data.room?.attendees && event.payload) {
      const votingAttendee = createAttendee(event.payload.attendee);
      const room = data.room.removeAttendeeByUserId(votingAttendee.user.id);
      room.attendees.push(votingAttendee);
      room.votingStatus = event.payload.votingStatus;
      setData(previousState => {
        return {
          ...previousState,
          room
        };
      });
    }
  }, [data.room]);
};
