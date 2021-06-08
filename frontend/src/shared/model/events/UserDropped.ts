import { User } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserDroppedPayload {
  user: User
}

export interface UserDroppedEvent extends Event<EventType.UserDropped, UserDroppedPayload>{ }
