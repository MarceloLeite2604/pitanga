import { Event } from './Event';
import { EventType } from './EventType';

export interface RevealVotesEvent extends Event<EventType.RevealVotes> { }

export function buildRevealVotesEvent() {
  return {
    type: EventType.RevealVotes
  } as RevealVotesEvent;
}
