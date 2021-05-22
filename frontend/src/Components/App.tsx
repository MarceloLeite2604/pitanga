import { useCallback, useEffect, useState } from 'react';
import { Switch, Route, useHistory } from 'react-router-dom';
import { Subscription } from 'rxjs';

import {
  Room,
  User,
  EventType,
  Event,
  CheckRoomExistsPayload,
  UserJoinedPayload,
  RoomCreatedPayload,
  UserCreatedPayoad,
  Attendee,
  UserDroppedPayload
} from '../Model';
import { Home } from './Home';
import { usePitangaWebSocket } from '../Hooks';
import { Room as RoomPage } from './Room';

export function App() {
  const [connected, setConnected] = useState(false);
  const [room, setRoom] = useState<Room>();
  const [user, setUser] = useState<User>();
  const [attendee, setAttendee] = useState<Attendee>();
  const pitangaWebSocket = usePitangaWebSocket();
  const history = useHistory();
  const [connectionSubscription, setConnectionSubscription] = useState<Subscription>();
  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();

  const eventsCallback = useCallback((incomingEvent: Event<any>) => {
    console.log(`Received event "${incomingEvent.type}"`);
    switch (incomingEvent.type) {
      case EventType.RoomCreated: {
        const payload = incomingEvent.payload as RoomCreatedPayload;
        setRoom(payload.room);
        setAttendee(payload.room.attendees[0]);
        history.push(`/${payload.room.id}`);
        break;
      }
      case EventType.UserCreated: {
        const payload = incomingEvent.payload as UserCreatedPayoad;
        setUser(payload.user);
        break;
      }
      case EventType.CheckRoomExists: {
        const payload = incomingEvent.payload as CheckRoomExistsPayload;
        if (!payload.exists) {
          setRoom(undefined);
          history.push('/');
        } else {
          if (!room) {
            setRoom(payload.room);
          }
        }
        break;
      }
      case EventType.MaxUsersReached: {
        pitangaWebSocket.$connected.next(false);
        break;
      }
      case EventType.UserJoined: {
        const payload = incomingEvent.payload as UserJoinedPayload;
        if (room && room.id === payload.room.id) {
          setRoom(payload.room);
          if (!attendee) {
            const roomAttendee = payload.room.attendees.find(att => att?.user.id === user?.id);
            if (roomAttendee) {
              setAttendee(roomAttendee);
            }
          }
        }
        break;
      }
      case EventType.UserDropped: {
        const payload = incomingEvent.payload as UserDroppedPayload;
        debugger;
        if (room) {
          const remainingAttendees = room?.attendees.filter(att => att.user.id !== payload.user.id);
          room.attendees = remainingAttendees;
          setRoom(room);
        }
        break;
      }
      default: {
        console.log(`[ERROR] Don't know how to handle "${incomingEvent.type}" event.`);
      }
    }
  }, [history, room]);

  const connectionCallback = useCallback((conn: boolean) => {
    setConnected(conn);
  }, []);

  useEffect(() => {
    eventsSubscription?.unsubscribe();
    connectionSubscription?.unsubscribe();

    setEventsSubscription(pitangaWebSocket.$incomingEvent.subscribe(eventsCallback));
    setConnectionSubscription(pitangaWebSocket.$connected.subscribe(connectionCallback));

    return () => {
      connectionSubscription?.unsubscribe();
      eventsSubscription?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);

  return (
    <Switch>
      <Route path='/:roomId'>
        <RoomPage
          connected={connected}
          user={user}
          attendee={attendee}
          room={room}
          pitangaWebSocket={pitangaWebSocket} />
      </Route>
      <Route path='/'>
        <Home
          connected={connected}
          user={user}
          pitangaWebSocket={pitangaWebSocket} />
      </Route>
    </Switch>);
}
