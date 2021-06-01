import { Switch, Route } from 'react-router-dom';
import { Container, Title, Home, Room } from './components';
import { useSubscriptions, useData } from './hooks';

export function App() {

  const [data, setData] = useData();
  useSubscriptions(data, setData);
  
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
