import { useEffect } from 'react';
import { Subscription } from 'rxjs';
import { useStatePartialUpdate } from '../../../hooks';
import { useWebSocketCallbacks } from './useWebSocketCallbacks';
import { Data } from '../../../Model';

interface Subscriptions {
  connection?: Subscription,
  events?: Subscription
}

export const useSubscriptions = (
  data: Data,
  setData: (newState: any) => void
) => {

  const [subscriptions, setSubscriptions] = useStatePartialUpdate<Subscriptions>({});
  const [eventsCallback, connectionCallback] = useWebSocketCallbacks(data, setData);

  useEffect(() => {
    subscriptions.events?.unsubscribe();
    subscriptions.connection?.unsubscribe();

    const events = data.subjects.$in.subscribe(eventsCallback);
    const connection = data.subjects.$connected.subscribe(connectionCallback);

    setSubscriptions({
      events,
      connection
    });

    return () => {
      subscriptions.events?.unsubscribe();
      subscriptions.connection?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);
};
