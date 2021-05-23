import { useState } from 'react';

export const useStatePartialUpdate = <T extends Object>(def: T | (() => T)) : [T, (newState: Partial<T>) => void] => {
  const [data, innerSetData] = useState<T>(def);

  const setData = (newState: Partial<T>) => {
    innerSetData(previousState => {
      return { ...previousState, ...newState };
    });
  };

  return [data, setData];
};
