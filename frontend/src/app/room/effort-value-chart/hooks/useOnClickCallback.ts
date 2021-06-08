import { useCallback } from 'react';
import {
  Attendee,
  buildAttendeeVotedEvent,
  ChartEventProps,
  createVote,
  Data
} from '../../../../shared/model';

export const useOnClickCallback = (data: Data) => {
  return useCallback((props: ChartEventProps) => {
    if (props && data.user && data.room?.attendees) {
      const { xValue, yValue } = props;
      const vote = createVote(xValue, yValue);
      const attendee = { ...data.attendee, vote } as Attendee;
      data.subjects.$out.next(buildAttendeeVotedEvent(attendee));
    }
  }, [data]);
};
