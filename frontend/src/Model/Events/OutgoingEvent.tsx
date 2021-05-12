enum OutgoingEventType {
  CreateRoom = 'create-room'
};

export interface OutgoingEvent<T> {
  type: OutgoingEventType,
  payload?: T
}

export function buildCreateRoomEvent() {
  return {
    type: OutgoingEventType.CreateRoom
  } as OutgoingEvent<null>;
};
