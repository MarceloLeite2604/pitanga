import { useCallback, useEffect, useState } from 'react';
import { Subscription } from 'rxjs';
import { useWebSocketCallbacks } from './useWebSocketCallbacks';
import { CallbackHookParams } from './types';
import { toast as showToast, ToastOptions } from 'react-toastify';
import { Toast, ToastType } from '../../shared/model';

interface Subscriptions {
  connection?: Subscription,
  events?: Subscription,
  toasts?: Subscription
}

const toastOptions: ToastOptions = {
  position: 'top-right',
  autoClose: 3000,
  hideProgressBar: false,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: false,
  progress: undefined
};

export const useSubscriptions = (params: CallbackHookParams) => {

  const [subscriptions, setSubscriptions] = useState<Subscriptions>();
  const [eventsCallback, connectionCallback] = useWebSocketCallbacks(params);
  const toastsCallback = useCallback((toast: Toast) => {
    switch (toast.type) {
      case ToastType.INFORMATION: {
        showToast.info(toast.message, toastOptions);
        break;
      }
      case ToastType.ERROR: {
        showToast.error(toast.message, toastOptions);
        break;
      }
      case ToastType.WARNING: {
        showToast.warn(toast.message, toastOptions);
        break;
      }
      default:
        showToast(toast.message, toastOptions);
    }
  }, []);
  const [data] = params;

  useEffect(() => {
    subscriptions?.events?.unsubscribe();
    subscriptions?.connection?.unsubscribe();
    subscriptions?.toasts?.unsubscribe();

    const events = data.subjects.$in.subscribe(eventsCallback);
    const connection = data.subjects.$connected.subscribe(connectionCallback);
    const toasts = data.subjects.$toast.subscribe(toastsCallback);

    setSubscriptions({
      events,
      connection,
      toasts
    });

    return () => {
      subscriptions?.events?.unsubscribe();
      subscriptions?.connection?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);
};
