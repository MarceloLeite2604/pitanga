import { useMemo } from 'react';
import { Data, VotingStatus } from '../../../../shared/model';

export const usePoints = (data: Data) => {
  return useMemo(() => {
    if (data.room?.attendees && data.user) {
      if (data.room.votingStatus === VotingStatus.Open) {
        const attendee = data.room?.findAttendeeByUser(data.user);
        if (attendee?.vote) {
          return [attendee.createChartPoint()];
        }
      } else {
        return data.room
          .attendees
          .filter(attendee => attendee.vote)
          .map(attendee => attendee.createChartPoint());
      }
    }
    return [];
  }, [data.room, data.user]);
};
