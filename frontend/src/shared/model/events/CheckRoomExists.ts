import { RoomDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface CheckRoomExistsPayload {
  room: RoomDto,
  exists?: boolean
}

export interface CheckRoomExistsEvent extends Event<EventType.CheckRoomExists, CheckRoomExistsPayload> { }

export function buildCheckRoomExists(room: RoomDto) {
  return {
    type: EventType.CheckRoomExists,
    payload: {
      room
    }
  } as CheckRoomExistsEvent;
}
