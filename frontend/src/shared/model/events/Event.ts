import { EventType } from './EventType';

export interface Event<T extends EventType = EventType, P extends object = object> {
  type: T,
  payload?: P
}
