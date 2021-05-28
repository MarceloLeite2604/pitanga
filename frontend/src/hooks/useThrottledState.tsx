import { useThrottleCallback } from '@react-hook/throttle';
import { SetStateAction, useState } from 'react';

export const useThrottledState: <T extends unknown>(initialState: T, fps?: number, leading?: boolean) => [T, (value: SetStateAction<T>) => void] =
<T extends unknown>(initialState: T, fps?: number, leading?: boolean) => {
  const [state, unthrottledSetState] = useState<T>(initialState);
  const setState = useThrottleCallback(unthrottledSetState, fps, leading);

  return [state, setState];
};
