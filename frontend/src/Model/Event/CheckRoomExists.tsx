import { Room } from '..';
import { EventType } from './EventType';

export interface CheckRoomExistsPayload {
  room: Room,
  exists?: boolean
}

export interface CheckRoomExistsEvent {
  type: EventType.CheckRoomExists,
  payload: CheckRoomExistsPayload
}

export function buildCheckRoomExists(room: Room) {
  return {
    type: EventType.CheckRoomExists,
    payload: {
      room
    }
  } as CheckRoomExistsEvent;
}
