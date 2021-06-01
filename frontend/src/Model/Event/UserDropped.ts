import { User } from '..';
import { EventType } from './EventType';

export interface UserDroppedPayload {
  user: User
}

export interface UserDroppedEvent {
  type: EventType.UserDropped,
  payload: UserDroppedPayload
}
