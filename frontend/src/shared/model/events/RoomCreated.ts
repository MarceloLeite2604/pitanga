import { RoomDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface RoomCreatedPayload {
  room: RoomDto
}

export interface RoomCreatedEvent extends Event<EventType.RoomCreated, RoomCreatedPayload>{ }
