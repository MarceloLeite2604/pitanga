import { Event } from './Event';
import { EventType } from './EventType';

export interface CreateUserEvent extends Event<EventType.CreateUser> {}
