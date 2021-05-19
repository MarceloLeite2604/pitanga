import { FC, useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Subscription } from 'rxjs';
import { PitangaWebSocket } from '../../Hooks';
import { Room as RoomModel, User, buildCheckRoomExists, EventType, CheckRoomExistsPayload, buildJoinUserEvent } from '../../Model';

interface Props {
  connected: boolean,
  user?: User,
  room?: RoomModel,
  pitangaWebSocket: PitangaWebSocket
}

export const Room: FC<Props> = ({ connected, user, room, pitangaWebSocket }) => {

  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();
  const { roomId } = useParams<{ roomId: string }>();

  const eventsCallback = useCallback(event => {
    if (event.type === EventType.CheckRoomExists) {
      const payload = event.payload as CheckRoomExistsPayload;
      if (payload.exists) {
        console.log('Sending join user event');
        user && pitangaWebSocket.$outgoingEvent.next(buildJoinUserEvent(user, payload.room));
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
    content = <p>{`Welcome user ${user?.id} to room #${room?.id}.`}</p>;
  } else {
    content = <p>Checking room...</p>;
  }

  return (
    content
  );
};
