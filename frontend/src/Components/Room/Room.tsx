import { FC, useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Subscription } from 'rxjs';
import { PitangaWebSocket } from '../../Hooks';
import {
  Room as RoomModel,
  User,
  buildCheckRoomExists,
  EventType,
  CheckRoomExistsPayload,
  buildJoinUserEvent,
  Attendee
} from '../../Model';

interface Props {
  connected: boolean,
  user?: User,
  attendee?: Attendee
  room?: RoomModel,
  pitangaWebSocket: PitangaWebSocket
}

export const Room: FC<Props> = ({ connected, user, attendee, room, pitangaWebSocket }) => {

  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();
  const { roomId } = useParams<{ roomId: string }>();

  const eventsCallback = useCallback(event => {
    if (event.type === EventType.CheckRoomExists) {
      const payload = event.payload as CheckRoomExistsPayload;
      if (user && payload.exists) {
        pitangaWebSocket.$outgoingEvent.next(buildJoinUserEvent(user, payload.room));
      }
    }
  }, [user]);

  useEffect(() => {
    if (connected && user) {
      eventsSubscription?.unsubscribe();
      setEventsSubscription(pitangaWebSocket.$incomingEvent.subscribe(eventsCallback));

      pitangaWebSocket.$outgoingEvent.next(buildCheckRoomExists({
        id: parseInt(roomId)
      } as RoomModel));
      return () => eventsSubscription?.unsubscribe();
    }
  }, [connected, user, roomId]);

  let content;
  if (room && user) {
    content = [<p key='1' >Welcome user {user?.id} to room #{room?.id}.</p>];
    if (attendee) {
      content = [...content, <p key='2'>Your icon is ${attendee.icon}</p>];

      if (room.attendees.length > 1) {
        const attendeesElement = room.attendees.filter(att => att !== attendee).map(
          (att, index) => <p key={4 + index}>User {att.user.id} with icon {att.icon}</p>
        );
        content = [...content, <p key='3'>Attendees in this room:</p>, ...attendeesElement];
      }
    }
    
  } else {
    content = <p>Checking room...</p>;
  }

  return (
    <>
      {content}
    </>
  );
};
