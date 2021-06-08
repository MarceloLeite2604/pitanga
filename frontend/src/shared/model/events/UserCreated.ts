import { User } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserCreatedPayload {
  user: User
}

export interface UserCreatedEvent extends Event<EventType.UserCreated, UserCreatedPayload>{}
