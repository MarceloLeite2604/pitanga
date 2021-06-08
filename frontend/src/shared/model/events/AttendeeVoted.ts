import { Attendee } from '..';
import { VotingStatus } from '../VotingStatus';
import { Event } from './Event';
import { EventType } from './EventType';

export interface AttendeeVotedPayload {
  attendee: Attendee,
  votingStatus: VotingStatus
}

export interface AttendeeVotedEvent extends Event<EventType.AttendeeVoted, AttendeeVotedPayload> { }

export function buildAttendeeVotedEvent(attendee: Attendee) {
  return {
    type: EventType.AttendeeVoted,
    payload: {
      attendee
    }
  } as AttendeeVotedEvent;
}
