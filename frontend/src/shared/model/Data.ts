import { Subject } from 'rxjs';
import { Room, User, Attendee, Event, Toast } from '.';

export interface WebSocketSubjects {
  $connected: Subject<boolean>,
  $in: Subject<Event>,
  $out: Subject<Event>,
  $toast: Subject<Toast>
}

export interface Data {
  connected: boolean,
  room?: Room,
  user?: User,
  attendee?: Attendee,
  subjects: WebSocketSubjects,
}
