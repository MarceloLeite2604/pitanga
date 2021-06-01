import { Room } from '../Room';
import { EventType } from './EventType';

export interface ResetRoomPayload {
  room: Room
}

export interface ResetRoomEvent {
  type: EventType.ResetRoom,
  payload: ResetRoomPayload
}

export function buildResetRoomEvent() {
  return {
    type: EventType.ResetRoom
  } as ResetRoomEvent;
}
