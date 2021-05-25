import { useCallback } from 'react';
import { useHistory } from 'react-router';
import {
  CheckRoomExistsEvent,
  Event,
  EventType,
  RoomCreatedEvent,
  UserCreatedEvent,
  UserDroppedEvent,
  UserJoinedEvent,
  Data
} from '../../../Model';

export const useWebSocketCallbacks: (
  data: Data,
  setData: (newState: any) => void
) => [(event: Event) => void, (conn: boolean) => void] = (data, setData) => {

  const history = useHistory();
  const { user, room, attendee, subjects } = data;

  const roomCreatedEventCallback = useCallback((event: RoomCreatedEvent) => {
    setData({
      room: event.payload.room,
      attendee: event.payload.room.attendees[0]
    });
    history.push(`/${event.payload.room.id}`);
  }, [history]);

  const userCreatedEventCallback = useCallback((event: UserCreatedEvent) => {
    setData({
      user: event.payload.user
    });
  }, []);

  const checkRoomExistsEventCallback = useCallback((event: CheckRoomExistsEvent) => {
    if (!event.payload.exists) {
      setData({
        room: undefined
      });
      history.push('/');
    } else {
      if (!room) {
        setData({
          room: event.payload.room
        });
      }
    }
  }, [history]);

  const maxUsersReachedEventCallback = useCallback(() => {
    subjects.$connected.next(false);
  }, []);

  const userJoinedEventCallback = useCallback((event: UserJoinedEvent) => {
    if (room && room.id === event.payload.room.id) {
      setData({ room: event.payload.room });
      if (!attendee) {
        const roomAttendee = event.payload.room.attendees.find(att => att?.user.id === user?.id);
        if (roomAttendee) {
          setData({ attendee: roomAttendee });
        }
      }
    }
  }, [room, attendee, user]);

  const userDroppedEventCallback = useCallback((event: UserDroppedEvent) => {
    if (room) {
      const remainingAttendees = room?.attendees.filter(att => att.user.id !== event.payload.user.id);
      setData({
        room: {
          ...room,
          attendees: remainingAttendees
        }
      });
    }
  }, [room]);

  const eventsCallback = useCallback((event: Event) => {
    console.log(`Received event "${event.type}"`);
    switch (event.type) {
      case EventType.RoomCreated: {
        roomCreatedEventCallback(event);
        break;
      }
      case EventType.UserCreated: {
        userCreatedEventCallback(event);
        break;
      }
      case EventType.CheckRoomExists: {
        checkRoomExistsEventCallback(event);
        break;
      }
      case EventType.MaxUsersReached: {
        maxUsersReachedEventCallback();
        break;
      }
      case EventType.UserJoined: {
        userJoinedEventCallback(event);
        break;
      }
      case EventType.UserDropped: {
        userDroppedEventCallback(event);
        break;
      }
      default: {
        console.log(`[ERROR] Don't know how to handle "${event.type}" event.`);
      }
    }
  }, [roomCreatedEventCallback,
    userCreatedEventCallback,
    checkRoomExistsEventCallback,
    maxUsersReachedEventCallback,
    userJoinedEventCallback,
    userDroppedEventCallback]);

  const connectionCallback = useCallback((conn: boolean) => {
    setData({ connected: conn });
  }, []);

  return [eventsCallback, connectionCallback];
};
