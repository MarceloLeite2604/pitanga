import { Attendee } from '..';
import { EventType } from './EventType';

export interface AttendeeVotedPayload {
  attendee: Attendee
}

export interface AttendeeVotedEvent {
  type: EventType.AttendeeVoted,
  payload: AttendeeVotedPayload
}

export function buildAttendeeVotedEvent(attendee: Attendee) {
  return {
    type: EventType.AttendeeVoted,
    payload: {
      attendee
    }
  } as AttendeeVotedEvent;
}
