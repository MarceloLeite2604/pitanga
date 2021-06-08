import { useWebSocketSubjects } from './useWebSocketSubjects';
import { Data } from '../../shared/model';
import { useState } from 'react';

export const useDataState = () => {

  const subjects = useWebSocketSubjects();

  return useState<Data>({
    connected: false,
    subjects
  });
};
