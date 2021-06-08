export enum EventType {
  CreateUser = 'create-user',
  UserCreated = 'user-created',
  MaxUsersReached = 'max-users-reached',
  JoinUser = 'join-user',
  UserJoined = 'user-joined',
  MaxRoomUsersReached = 'max-room-users-reached',
  CreateRoom = 'create-room',
  RoomCreated = 'room-created',
  MaxRoomsReached = 'max-rooms-reached',
  CheckRoomExists = 'check-room-exists',
  UserDropped = 'user-dropped',
  AttendeeVoted = 'attendee-voted',
  ResetRoom = 'reset-room',
  RevealVotes = 'reveal-votes'
}

// export type EventType =
//   'create-user' |
//   'user-created' |
//   'max-users-reached' |
//   'join-user' |
//   'user-joined' |
//   'max-room-users-reached' |
//   'create-room' |
//   'room-created' |
//   'max-rooms-reached' |
//   'check-room-exists' |
//   'user-dropped' |
//   'attendee-voted' |
//   'reset-room' |
//   'reveal-votes';
