import {
  AttendeeDto,
  VotingStatus,
  createAttendee
} from '.';
import { Attendee } from './Attendee';
import { User } from './User';

export interface RoomDto {
  id: number,
  attendees: AttendeeDto[],
  votingStatus: VotingStatus,
}

export type Room = Omit<RoomDto, 'attendees'> & {
  attendees: Attendee[],
  findAttendeeByUser: (user: User) => Attendee | undefined,
  removeAttendeeByUserId: (id: string) => Room,
  sortAttendeesByJoinedAtWithUserFirst: (user: User) => Room
};

export const createRoom = ({
  id,
  attendees: atts,
  votingStatus
} = {} as RoomDto): Room => {
  const attendees = atts.map(attendee => createAttendee(attendee));
  return {
    id,
    attendees,
    votingStatus,
    findAttendeeByUser(user: User): Attendee | undefined {
      if (this.attendees.length === 0 || !user) {
        return undefined;
      }
    
      return this.attendees.filter(attendee => attendee.user.id === user.id)[0];
    },
    sortAttendeesByJoinedAtWithUserFirst(user: User): Room {
      const sortedAttendees = [...this.attendees];
      sortedAttendees.sort((first, second) => {
        if (first.user.id === user.id) {
          return -1;
        }
    
        if (second.user.id === user.id) {
          return 1;
        }
    
        return first.joinedAt - second.joinedAt;
      });
      return {
        ...this,
        attendees: sortedAttendees
      };
    },
    removeAttendeeByUserId(userId: string): Room {
      if (this.attendees.length === 0 || !userId) {
        return { ...this };
      }
    
      return {
        ...this,
        attendees: this.attendees.filter(attendee => attendee.user.id !== userId)
      };
    }
  };
};
