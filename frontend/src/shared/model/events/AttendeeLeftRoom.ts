import { Attendee } from '../';
import { Event } from './Event';
import { EventType } from './EventType';

export interface AttendeeLeftRoomPayload {
  attendee: Attendee,
  newRoomOwner?: Attendee
}

export interface AttendeeLeftRoomEvent extends Event<EventType.AttendeeLeftRoom, AttendeeLeftRoomPayload> { }

export function buildAttendeeLeftRoomEvent(attendee: Attendee) {
  return {
    type: EventType.AttendeeLeftRoom,
    payload: {
      attendee
    }
  } as AttendeeLeftRoomEvent;
}
