import { UserDto, RoomDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserJoinedPayload {
  room: RoomDto,
  user: UserDto
}

export interface UserJoinedEvent extends Event<EventType.UserJoined, UserJoinedPayload> { }
