export interface Vote {
  effort: number,
  value: number
};

export const createVote = (x: number, y: number) => {
  return {
    value: Math.round(x),
    effort: Math.round(y)
  } as Vote;
};
