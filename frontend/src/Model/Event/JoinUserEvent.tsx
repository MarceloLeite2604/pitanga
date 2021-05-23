import { EventType } from './EventType';
import { User, Room } from '..';

export interface JoinUserPayload {
  room: Room,
  user: User
}

export interface JoinUserEvent {
  type: EventType.JoinUser,
  payload: JoinUserPayload
}

export function buildJoinUserEvent(user: User, room: Room) {
  return {
    type: EventType.JoinUser,
    payload: {
      user,
      room
    }
  } as JoinUserEvent;
}
