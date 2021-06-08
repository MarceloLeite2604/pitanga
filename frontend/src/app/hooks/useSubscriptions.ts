import { useEffect, useState } from 'react';
import { Subscription } from 'rxjs';
import { useWebSocketCallbacks } from './useWebSocketCallbacks';
import { CallbackHookParams } from './types';

interface Subscriptions {
  connection?: Subscription,
  events?: Subscription
}

export const useSubscriptions = (params: CallbackHookParams) => {

  const [subscriptions, setSubscriptions] = useState<Subscriptions>();
  const [eventsCallback, connectionCallback] = useWebSocketCallbacks(params);
  const [data] = params;

  useEffect(() => {
    subscriptions?.events?.unsubscribe();
    subscriptions?.connection?.unsubscribe();

    const events = data.subjects.$in.subscribe(eventsCallback);
    const connection = data.subjects.$connected.subscribe(connectionCallback);

    setSubscriptions({
      events,
      connection
    });

    return () => {
      subscriptions?.events?.unsubscribe();
      subscriptions?.connection?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);
};
