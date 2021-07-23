import { AttendeeDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface AttendeeLeftRoomPayload {
  attendee: AttendeeDto,
  newRoomOwner?: AttendeeDto
}

export interface AttendeeLeftRoomEvent extends Event<EventType.AttendeeLeftRoom, AttendeeLeftRoomPayload> { }

export function buildAttendeeLeftRoomEvent(attendee: AttendeeDto) {
  return {
    type: EventType.AttendeeLeftRoom,
    payload: {
      attendee
    }
  } as AttendeeLeftRoomEvent;
}
