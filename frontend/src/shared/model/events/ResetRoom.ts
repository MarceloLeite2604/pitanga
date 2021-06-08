import { Room } from '../Room';
import { Event } from './Event';
import { EventType } from './EventType';

export interface ResetRoomPayload {
  room: Room
}

export interface ResetRoomEvent extends Event<EventType.ResetRoom, ResetRoomPayload> {
}

export function buildResetRoomEvent() {
  return {
    type: EventType.ResetRoom
  } as ResetRoomEvent;
}
