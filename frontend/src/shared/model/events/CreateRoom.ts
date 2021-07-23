import { UserDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface CreateRoomPayload {
  user: UserDto
}

export interface CreateRoomEvent extends Event<EventType.CreateRoom, CreateRoomPayload> { }

export function buildCreateRoomEvent(user: UserDto) {
  return {
    type: EventType.CreateRoom,
    payload: {
      user
    }
  } as CreateRoomEvent;
}
