import { Room } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface RoomCreatedPayload {
  room: Room
}

export interface RoomCreatedEvent extends Event<EventType.RoomCreated, RoomCreatedPayload>{ }
