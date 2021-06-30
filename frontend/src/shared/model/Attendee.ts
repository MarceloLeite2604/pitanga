import { ChartPoint } from './ChartPoint';
import { User } from './User';
import { Vote } from './Vote';

export interface Attendee {
  user: User,
  icon: string,
  joinedAt: number,
  roomOwner: boolean
  vote?: Vote
}

export const sortByJoinedAtWithCurrentUserFirst = (attendees: Attendee[], user: User) => {
  return attendees.sort((first, second) => {
    if (first.user.id === user.id) {
      return -1;
    }

    if (second.user.id === user.id) {
      return 1;
    }

    return first.joinedAt - second.joinedAt;
  });
};

export const removeAttendeeFromAttendees = (attendee: Attendee, attendees: Attendee[]) => {
  if (attendees.length === 0 || !attendee) {
    return attendees;
  }

  return attendees.filter(att => att.user.id !== attendee.user.id);
};

export const removeUserFromAttendees = (user: User, attendees: Attendee[]) => {
  if (attendees.length === 0 || !user) {
    return attendees;
  }

  return attendees.filter(attendee => attendee.user.id !== user.id);
};

export const retrieveAttendeeForUser = (attendees: Attendee[], user: User) => {
  if (attendees.length === 0 || !user) {
    return undefined;
  }

  return attendees.filter(attendee => attendee.user.id === user.id)[0];
};

export const createChartPoint = (attendee: Attendee) => {
  return {
    effort: attendee.vote?.effort,
    value: attendee.vote?.value,
    label: attendee.icon
  } as ChartPoint;
};
