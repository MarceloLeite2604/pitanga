import { Grid } from '@material-ui/core';
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
      <Grid
        container
        direction='row'
        spacing={1}>
        <Grid
          item
          xs={12}>
          <Title />
        </Grid>
        <Grid
          item
          xs={12}>
          <Switch>
            <Route path='/:roomId'>
              <Room data={data} />
            </Route>
            <Route path='/'>
              <Home data={data} />
            </Route>
          </Switch>
        </Grid>
      </Grid>
    </Container>
  );
}
