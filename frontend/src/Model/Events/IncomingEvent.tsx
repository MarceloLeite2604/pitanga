export enum IncomingEventType {
  RoomCreated = 'room-created',
  UserJoined = 'user-joined'
};

export interface IncomingEvent<T> {
  type: IncomingEventType,
  payload?: T
};
