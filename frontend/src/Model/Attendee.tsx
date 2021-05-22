import { User } from './User';

export interface Attendee {
  user: User,
  icon: string,
  joinedAt: number
}
