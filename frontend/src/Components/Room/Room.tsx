import { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Subscription } from 'rxjs';
import {
  Room as RoomModel,
  Data,
  buildCheckRoomExists,
  EventType,
  CheckRoomExistsPayload,
  buildJoinUserEvent
} from '../../Model';

export const Room = (data: Data) => {

  const { connected, room, user, attendee, subjects } = data;

  console.log('Rendering Room.');
  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();
  const { roomId } = useParams<{ roomId: string }>();
  const [, updateState] = useState<Object>();
  useCallback(() => {
    console.log('Forcing update');
    updateState({});
  }, [room]);

  const eventsCallback = useCallback(event => {
    if (event.type === EventType.CheckRoomExists) {
      const payload = event.payload as CheckRoomExistsPayload;
      if (user && payload.exists) {
        subjects.$out.next(buildJoinUserEvent(user, payload.room));
      }
    }
  }, [user]);

  useEffect(() => {
    if (connected && user) {
      eventsSubscription?.unsubscribe();
      setEventsSubscription(subjects.$in.subscribe(eventsCallback));

      subjects.$out.next(buildCheckRoomExists({
        id: parseInt(roomId)
      } as RoomModel));
      return () => eventsSubscription?.unsubscribe();
    }
  }, [connected, user, roomId]);

  let content;
  if (room && user) {
    content = [<p key='1' >Welcome user {user?.id} to room #{room?.id}.</p>];
    if (attendee) {
      content = [...content, <p key='2'>Your icon is {attendee.icon}</p>];

      if (room.attendees.length > 1) {
        const attendeesElement = room.attendees.filter(att => att.user.id !== attendee.user.id).map(
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
