import { useCallback, useMemo } from 'react';
import { EventType, Event } from '../../../shared/model';
import { CallbackHookParams, EventCallback } from '../types';
import { useAttendeeVotedCallback } from './useAttendeeVotedCallback';
import { useRoomCreatedCallback } from './useRoomCreatedCallback';
import { useUserCreatedCallback } from './useUserCreatedCallback';
import { useCheckRoomExistsCallback } from './useCheckRoomExistsCallback';
import { useMaxUsersReachedCallback } from './useMaxUsersReachedCallback';
import { useResetRoomCallback } from './useResetRoomCallback';
import { useRevealVotesCallback } from './useRevealVotesCallback';
import { useUserDroppedCallback } from './useUserDroppedCallback';
import { useUserJoinedCallback } from './useUserJoinedCallback';
import { useAttendeeLeftRoomCallback } from './useAttendeeLeftRoomCallback';

type EventsCallbacks = { [key in EventType]?: EventCallback<any> };

export const useEventsCallback = (params: CallbackHookParams) => {
  const attendeeVotedCallback = useAttendeeVotedCallback(params);
  const roomCreatedCallback = useRoomCreatedCallback(params);
  const userCreatedCallback = useUserCreatedCallback(params);
  const checkRoomExistsCallback = useCheckRoomExistsCallback(params);
  const maxUsersReachedCallback = useMaxUsersReachedCallback(params);
  const userJoinedCallback = useUserJoinedCallback(params);
  const userDroppedCallback = useUserDroppedCallback(params);
  const revealVotesCallback = useRevealVotesCallback(params);
  const resetRoomCallback = useResetRoomCallback(params);
  const attendeeLeftRoomEventCallback = useAttendeeLeftRoomCallback(params);

  const callbacks = useMemo(() => {
    return {
      [EventType.UserCreated]: userCreatedCallback,
      [EventType.CheckRoomExists]: checkRoomExistsCallback,
      [EventType.AttendeeVoted]: attendeeVotedCallback,
      [EventType.RoomCreated]: roomCreatedCallback,
      [EventType.ResetRoom]: resetRoomCallback,
      [EventType.RevealVotes]: revealVotesCallback,
      [EventType.UserDropped]: userDroppedCallback,
      [EventType.UserJoined]: userJoinedCallback,
      [EventType.MaxUsersReached]: maxUsersReachedCallback,
      [EventType.AttendeeLeftRoom]: attendeeLeftRoomEventCallback
    } as EventsCallbacks;
  }, [attendeeVotedCallback,
    roomCreatedCallback,
    userCreatedCallback,
    checkRoomExistsCallback,
    maxUsersReachedCallback,
    userJoinedCallback,
    userDroppedCallback,
    revealVotesCallback,
    resetRoomCallback,
    attendeeLeftRoomEventCallback
  ]);

  return useCallback((event: Event) => {
    console.log(`Received event "${event.type}".`);
    const callback = callbacks[event.type];
    if (!callback) {
      console.log(`[ERROR] Don't know how to handle "${event.type}" event.`);
      return;
    }
    callback(event);
  }, [callbacks]);
};
