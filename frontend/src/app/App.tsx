import { Switch, Route } from 'react-router-dom';
import { Container } from './container';
import { Home } from './home';
import { useSubscriptions, useDataState } from './hooks';
import { Room } from './room';
import { Title } from './Title';

export function App() {

  const dataState = useDataState();
  useSubscriptions(dataState);
  const [data] = dataState;
  
  return (
    <Container>
      <Title />
      <Switch>
        <Route path='/:roomId'>
          <Room
            data={data} />
        </Route>
        <Route path='/'>
          <Home data={data} />
        </Route>
      </Switch>
    </Container>
  );
}
