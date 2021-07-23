import { UserDto, RoomDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface JoinUserPayload {
  room: RoomDto,
  user: UserDto
}

export interface JoinUserEvent extends Event<EventType.JoinUser, JoinUserPayload> { }

export function buildJoinUserEvent(user: UserDto, room: RoomDto) {
  return {
    type: EventType.JoinUser,
    payload: {
      user,
      room
    }
  } as JoinUserEvent;
}
