import { Room } from '../Room';
import { User } from '../User';

enum EventType {
  CreateRoom = 'create-room',
  JoinUser = 'join-user',
  CheckRoomExists = 'check-room-exists',
  RoomCreated = 'room-created',
  UserJoined = 'user-joined'
};

export interface Event<T> {
  type: EventType,
  payload?: T
}

export function buildCreateRoomEvent(user: User) {
  return {
    type: EventType.CreateRoom,
    payload: user
  } as Event<User>;
};

export function buildJoinUserEvent() {
  return {
    type: EventType.JoinUser
  } as Event<null>;
}

export interface CheckRoomExistsPayload {
  id: number,
  exists ?: boolean
}

export function buildCheckRoomExists(room: Room) {
  return {
    type: EventType.CheckRoomExists,
    payload: {
      id: room.id
    }
  } as Event<CheckRoomExistsPayload>;
}
