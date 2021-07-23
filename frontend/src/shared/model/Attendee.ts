import {
  ChartPoint,
  UserDto,
  User,
  Vote,
  createUser
} from '.';

export interface AttendeeDto {
  user: UserDto,
  icon: string,
  joinedAt: number,
  roomOwner: boolean
  vote?: Vote,
}

export type Attendee = Omit<AttendeeDto, 'user'> & {
  user: User
  createChartPoint: () => ChartPoint
}

export const createAttendee = ({
  user,
  icon,
  joinedAt,
  roomOwner,
  vote
} = {} as AttendeeDto) => ({
  user: createUser(user),
  icon,
  joinedAt,
  roomOwner,
  vote,
  createChartPoint(): ChartPoint {
    return {
      effort: this.vote?.effort || 0,
      value: this.vote?.value || 0,
      label: this.icon
    };
  }
} as Attendee);
