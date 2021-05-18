import { User } from '../User';
import { Room } from '../Room';

export enum EventType {
  CreateUser = 'create-user',
  UserCreated = 'user-created',
  MaxUsersReached = 'max-users-reached',
  JoinUser = 'join-user',
  UserJoined = 'user-joined',
  MaxRoomUsersReached = 'max-room-users-reached',
  CreateRoom = 'create-room',
  RoomCreated = 'room-created',
  MaxRoomsReached = 'max-rooms-reached',
  CheckRoomExists = 'check-room-exists'
};

export interface CheckRoomExistsPayload {
  id: number,
  exists ?: boolean
}

export interface JoinUserPayload {
  room: Room,
  user: User
}

export interface Event<T> {
  type: EventType,
  payload?: T
}
