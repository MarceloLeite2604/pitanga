import { User } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface CreateRoomPayload {
  user: User
}

export interface CreateRoomEvent extends Event<EventType.CreateRoom, CreateRoomPayload> { }

export function buildCreateRoomEvent(user: User) {
  return {
    type: EventType.CreateRoom,
    payload: {
      user
    }
  } as CreateRoomEvent;
}
