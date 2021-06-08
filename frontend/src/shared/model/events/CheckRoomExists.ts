import { Room } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface CheckRoomExistsPayload {
  room: Room,
  exists?: boolean
}

export interface CheckRoomExistsEvent extends Event<EventType.CheckRoomExists, CheckRoomExistsPayload> { }

export function buildCheckRoomExists(room: Room) {
  return {
    type: EventType.CheckRoomExists,
    payload: {
      room
    }
  } as CheckRoomExistsEvent;
}
