import { useCallback } from 'react';
import { Event } from '../../shared/model';
import { useEventsCallback } from './events-callback';
import { CallbackHookParams } from './types';

export const useWebSocketCallbacks = (params: CallbackHookParams):
[(event: Event) => void, (conn: boolean) => void] => {

  const [, setData] = params;
  const connectionCallback = useCallback((connected: boolean) => {
    setData(previousState => {
      return {
        ...previousState,
        connected
      };
    });
  }, []);

  const eventsCallback = useEventsCallback(params);

  return [eventsCallback, connectionCallback];
};
