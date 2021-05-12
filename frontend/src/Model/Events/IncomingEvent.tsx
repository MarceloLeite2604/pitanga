export enum IncomingEventType {
  RoomCreated = 'room-created'
};

export interface IncomingEvent<T> {
  type: IncomingEventType,
  payload?: T
};
