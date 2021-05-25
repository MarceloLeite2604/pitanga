import { DependencyList, useCallback, useState } from 'react';

export const useUpdateState = (deps: DependencyList) => {
  const [, updateState] = useState<Object>();
  useCallback(() => {
    updateState({});
  }, [deps]);
};
