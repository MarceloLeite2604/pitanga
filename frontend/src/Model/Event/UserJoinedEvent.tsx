import { EventType } from './EventType';
import { User, Room } from '..';

export interface UserJoinedPayload {
  room: Room,
  user: User
}

export interface UserJoinedEvent {
  type: EventType.UserJoined,
  payload: UserJoinedPayload
}
