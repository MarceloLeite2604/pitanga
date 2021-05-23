import { Subject, Subscription } from 'rxjs';
import { Room, User, Attendee, Event } from '.';

export interface WebSocketSubjects {
  $connected: Subject<boolean>,
  $in: Subject<Event>,
  $out: Subject<Event>,
}

interface Subscriptions {
  connection?: Subscription,
  events?: Subscription
}

export interface Data {
  connected: boolean,
  room?: Room,
  user?: User,
  attendee?: Attendee,
  subscriptions: Subscriptions,
  subjects: WebSocketSubjects
}
