import { User } from './User';

export interface Attendee {
  user: User,
  icon: string,
  joinedAt: number
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
