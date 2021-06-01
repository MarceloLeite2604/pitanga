import { EventType } from './EventType';
import { User } from '..';

export interface UserCreatedPayload {
  user: User
}

export interface UserCreatedEvent {
  type: EventType.UserCreated,
  payload: UserCreatedPayload
}
