export interface UserDto {
  id: string
}

export type User = UserDto;

export const createUser = ({
  id
} = {} as UserDto) => ({
  id
} as User);
