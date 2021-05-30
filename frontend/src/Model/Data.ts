import { Subject } from 'rxjs';
import { Room, User, Attendee, Event } from '.';

export interface WebSocketSubjects {
  $connected: Subject<boolean>,
  $in: Subject<Event>,
  $out: Subject<Event>,
}

export interface Data {
  connected: boolean,
  room?: Room,
  user?: User,
  attendee?: Attendee,
  subjects: WebSocketSubjects,
}
