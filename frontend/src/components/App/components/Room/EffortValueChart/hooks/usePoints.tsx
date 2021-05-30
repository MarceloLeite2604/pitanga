import { useMemo } from 'react';
import { createChartPoint, Data, retrieveAttendeeForUser } from '../../../../../../Model';

export const usePoints = (data: Data) => {
  return useMemo(() => {
    if (data.room?.attendees && data.user) {
      const attendee = retrieveAttendeeForUser(data.room?.attendees, data.user);
      if (attendee?.vote) {
        return [createChartPoint(attendee)];
      }
    }
    return [];
  }, [data.room, data.user]);
};
