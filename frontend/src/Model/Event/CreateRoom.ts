import { User } from '..';
import { EventType } from './EventType';

export interface CreateRoomPayload {
  user: User
}

export interface CreateRoomEvent {
  type: EventType.CreateRoom,
  payload: CreateRoomPayload
}

export function buildCreateRoomEvent(user: User) {
  return {
    type: EventType.CreateRoom,
    payload: {
      user
    }
  } as CreateRoomEvent;
}
