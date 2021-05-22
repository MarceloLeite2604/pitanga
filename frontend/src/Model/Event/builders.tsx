import {
  Event,
  EventType,
  CheckRoomExistsPayload,
  JoinUserPayload,
  CreateRoomPayload
} from './Event';
import { Room } from '../Room';
import { User } from '../User';

export function buildJoinUserEvent(user: User, room: Room) {
  return {
    type: EventType.JoinUser,
    payload: {
      user,
      room
    }
  } as Event<JoinUserPayload>;
};

export function buildCreateRoomEvent(user: User) {
  return {
    type: EventType.CreateRoom,
    payload: {
      user
    }
  } as Event<CreateRoomPayload>;
};

export function buildCheckRoomExists(room: Room) {
  return {
    type: EventType.CheckRoomExists,
    payload: {
      room
    }
  } as Event<CheckRoomExistsPayload>;
}
