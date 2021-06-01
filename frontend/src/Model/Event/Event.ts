import {
  CheckRoomExistsEvent,
  CreateRoomEvent,
  CreateUserEvent,
  JoinUserEvent,
  MaxRoomsReachedEvent,
  MaxRoomsUsersReachedEvent,
  MaxUsersReachedEvent,
  RoomCreatedEvent,
  UserCreatedEvent,
  UserDroppedEvent,
  UserJoinedEvent,
  AttendeeVotedEvent,
  ResetRoomEvent,
  RevealVotesEvent
} from '.';

export type Event = CheckRoomExistsEvent |
CreateRoomEvent |
CreateUserEvent |
JoinUserEvent |
MaxRoomsReachedEvent |
MaxRoomsUsersReachedEvent |
MaxUsersReachedEvent |
RoomCreatedEvent |
UserCreatedEvent |
UserDroppedEvent |
UserJoinedEvent |
AttendeeVotedEvent |
ResetRoomEvent |
RevealVotesEvent;
