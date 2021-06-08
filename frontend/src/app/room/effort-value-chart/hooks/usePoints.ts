import { useMemo } from 'react';
import { createChartPoint, Data, retrieveAttendeeForUser, VotingStatus } from '../../../../shared/model';

export const usePoints = (data: Data) => {
  return useMemo(() => {
    if (data.room?.attendees && data.user) {
      if (data.room.votingStatus === VotingStatus.Open) {
        const attendee = retrieveAttendeeForUser(data.room?.attendees, data.user);
        if (attendee?.vote) {
          return [createChartPoint(attendee)];
        }
      } else {
        return data.room
          .attendees
          .filter(attendee => attendee.vote)
          .map(attendee => createChartPoint(attendee));
      }
    }
    return [];
  }, [data.room, data.user]);
};
