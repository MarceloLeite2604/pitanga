import { Attendee } from './Attendee';
import { VotingStatus } from './VotingStatus';

export interface Room {
  id: number,
  attendees: Attendee[],
  votingStatus: VotingStatus
};
