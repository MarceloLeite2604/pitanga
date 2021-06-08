import { User, Room } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserJoinedPayload {
  room: Room,
  user: User
}

export interface UserJoinedEvent extends Event<EventType.UserJoined, UserJoinedPayload> { }
