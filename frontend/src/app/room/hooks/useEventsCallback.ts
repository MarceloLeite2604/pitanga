import { useCallback, useEffect, useState } from 'react';
import { useParams } from 'react-router';
import { Subscription } from 'rxjs';
import {
  buildCheckRoomExists,
  buildJoinUserEvent,
  Data,
  Event,
  Room
} from '../../../shared/model';

export const useEventsCallback = (data: Data) => {

  const [eventsSubscription, setEventsSubscription] = useState<Subscription>();
  const { roomId } = useParams<{ roomId: string }>();

  const { connected, user, subjects } = data;
  const eventsCallback = useCallback((event: Event<any, any>) => {
    if (event.type === 'check') {
      const payload = event.payload;
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
      } as Room));
      return () => eventsSubscription?.unsubscribe();
    }
  }, [connected, user, roomId]);
};
