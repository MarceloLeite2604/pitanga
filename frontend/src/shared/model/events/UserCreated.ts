import { UserDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserCreatedPayload {
  user: UserDto
}

export interface UserCreatedEvent extends Event<EventType.UserCreated, UserCreatedPayload>{}
