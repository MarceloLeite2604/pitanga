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
