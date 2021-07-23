export interface VoteDto {
  effort: number,
  value: number
}

export type Vote = VoteDto;

export const createVote = (x: number, y: number) => ({
  value: Math.round(x),
  effort: Math.round(y)
} as Vote);
