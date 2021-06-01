import { Room } from '..';
import { EventType } from './EventType';

export interface RoomCreatedPayload {
  room: Room
}

export interface RoomCreatedEvent {
  type: EventType.RoomCreated,
  payload: RoomCreatedPayload
}
