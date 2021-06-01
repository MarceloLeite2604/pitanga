import { EventType } from './EventType';

export interface RevealVotesEvent {
  type: EventType.RevealVotes
}

export function buildRevealVotesEvent() {
  return {
    type: EventType.RevealVotes
  } as RevealVotesEvent;
}
