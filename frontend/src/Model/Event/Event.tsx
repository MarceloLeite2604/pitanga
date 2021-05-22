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
  CheckRoomExists = 'check-room-exists',
  UserDropped = 'user-dropped'
}

export interface RoomCreatedPayload {
  room: Room
}

export interface UserCreatedPayoad {
  user: User
}

export interface CreateRoomPayload {
  user: User
}

export interface CheckRoomExistsPayload {
  room: Room,
  exists ?: boolean
}

export interface JoinUserPayload {
  room: Room,
  user: User
}

export interface UserJoinedPayload {
  room: Room,
  user: User
}

export interface UserDroppedPayload {
  user: User
}

export interface Event<T> {
  type: EventType,
  payload?: T
}
