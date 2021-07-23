import { AttendeeDto } from '..';
import { VotingStatus } from '../VotingStatus';
import { Event } from './Event';
import { EventType } from './EventType';

export interface AttendeeVotedPayload {
  attendee: AttendeeDto,
  votingStatus: VotingStatus
}

export interface AttendeeVotedEvent extends Event<EventType.AttendeeVoted, AttendeeVotedPayload> { }

export function buildAttendeeVotedEvent(attendee: AttendeeDto) {
  return {
    type: EventType.AttendeeVoted,
    payload: {
      attendee
    }
  } as AttendeeVotedEvent;
}
