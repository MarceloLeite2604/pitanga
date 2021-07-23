import { useHistory } from 'react-router-dom';
import { buildAttendeeLeftRoomEvent } from '../../shared/model';
import { Location, UnregisterCallback } from 'history';
import { DependencyList, useCallback, useState } from 'react';
import { CallbackHookParams } from './types';

interface UnregisterCallbackControl {
  callback?: UnregisterCallback,
  deps?: DependencyList
}

export const useCheckAttendeeLeftRoomCallback = ([data]: CallbackHookParams) => {

  const history = useHistory();

  const deps: DependencyList = [data.room?.id, data.attendee];

  const checkAttendeeLeftRoomCallback = useCallback((location: Location) => {
    console.log(`[CB] Checking if user has left the room. Room id: ${data.room?.id}, pathname: ${location.pathname}`);
    if (data.room?.id) {
      if (!location.pathname.match(new RegExp(`/${data.room?.id}`))) {
        console.log('[CB] Sending "attendee-left-room" event.');
        data.attendee && data.subjects.$out.next(buildAttendeeLeftRoomEvent(data.attendee));
      }
    }
  }, [data.room?.id, data.attendee]);

  const [unregisterCallbackControl, setUnregisterCallbackControl] = useState<UnregisterCallbackControl>({});

  if (!unregisterCallbackControl.callback) {
    setUnregisterCallbackControl({
      callback: history.listen(checkAttendeeLeftRoomCallback),
      deps: deps
    });
  } else {
    if (!unregisterCallbackControl.deps?.every((element, index) => element === deps[index])) {
      unregisterCallbackControl.callback();
      setUnregisterCallbackControl({
        callback: history.listen(checkAttendeeLeftRoomCallback),
        deps: deps
      });
    }
  }
};
