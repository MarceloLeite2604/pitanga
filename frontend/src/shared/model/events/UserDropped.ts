import { UserDto, AttendeeDto } from '..';
import { Event } from './Event';
import { EventType } from './EventType';

export interface UserDroppedPayload {
  user: UserDto,
  newRoomOwner?: AttendeeDto
}

export interface UserDroppedEvent extends Event<EventType.UserDropped, UserDroppedPayload>{ }
