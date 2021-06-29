import { User, Attendee } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserDroppedPayload {
  user: User,
  newRoomOwner?: Attendee
}

export interface UserDroppedEvent extends Event<EventType.UserDropped, UserDroppedPayload>{ }
