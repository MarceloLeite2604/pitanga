import { useStatePartialUpdate } from '../../../hooks';
import { useWebSocketSubjects } from './useWebSocketSubjects';
import { Data } from '../../../Model';

export const useData = (): [Data, (newState: Partial<Data>) => void] => {

  const subjects = useWebSocketSubjects();

  const [data, setData] = useStatePartialUpdate<Data>({
    connected: false,
    subjects
  });

  return [data, setData];
};
