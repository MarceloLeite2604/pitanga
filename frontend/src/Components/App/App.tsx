import { Switch, Route } from 'react-router-dom';

import { Home, Room } from '..';
import { useSubscriptions, useData } from './hooks';

export function App() {

  const [data, setData] = useData();
  useSubscriptions(data, setData);

  return (
    <Switch>
      <Route path='/:roomId'>
        <Room data={data} />
      </Route>
      <Route path='/'>
        <Home data={data} />
      </Route>
    </Switch>);
}
