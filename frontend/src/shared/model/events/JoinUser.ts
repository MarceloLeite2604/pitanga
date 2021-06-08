import { User, Room } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface JoinUserPayload {
  room: Room,
  user: User
}

export interface JoinUserEvent extends Event<EventType.JoinUser, JoinUserPayload> { }

export function buildJoinUserEvent(user: User, room: Room) {
  return {
    type: EventType.JoinUser,
    payload: {
      user,
      room
    }
  } as JoinUserEvent;
}
