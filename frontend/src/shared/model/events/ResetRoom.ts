import { RoomDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface ResetRoomPayload {
  room: RoomDto
}

export interface ResetRoomEvent extends Event<EventType.ResetRoom, ResetRoomPayload> {
}

export function buildResetRoomEvent() {
  return {
    type: EventType.ResetRoom
  } as ResetRoomEvent;
}
