import { useCallback } from 'react';
import { Event, ToastType } from '../../shared/model';
import { useEventsCallback } from './events-callback';
import { CallbackHookParams } from './types';

export const useWebSocketCallbacks = (params: CallbackHookParams):
[(event: Event) => void, (conn: boolean) => void] => {

  const [data, setData] = params;
  const connectionCallback = useCallback((connected: boolean) => {
    if (data.connected) {
      if (!connected) {
        data.subjects.$toast.next({
          message: 'Disconnected from server.',
          type: ToastType.WARNING
        });
      }
    } else {
      if (!connected) {
        data.subjects.$toast.next({
          message: 'Could not connect with server.',
          type: ToastType.ERROR
        });
      }
    }
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
