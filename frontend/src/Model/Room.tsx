import { Attendee } from './Attendee';

export interface Room {
  id: number,
  attendees: Attendee[]
}
