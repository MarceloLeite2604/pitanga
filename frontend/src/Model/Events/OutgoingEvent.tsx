import { User } from '../User';

enum OutgoingEventType {
  CreateRoom = 'create-room',
  JoinUser = 'join-user'
};

export interface OutgoingEvent<T> {
  type: OutgoingEventType,
  payload?: T
}

export function buildCreateRoomEvent(user: User) {
  return {
    type: OutgoingEventType.CreateRoom,
    payload: user
  } as OutgoingEvent<User>;
};

export function buildJoinUserEvent() {
  return {
    type: OutgoingEventType.JoinUser
  } as OutgoingEvent<null>;
}
