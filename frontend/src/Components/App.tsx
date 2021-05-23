import { useEffect } from 'react';
import { Switch, Route } from 'react-router-dom';

import { Home } from './Home';
import { useWebSocketSubjects, useStatePartialUpdate, useWebSocketCallbacks } from '../Hooks';
import { Room as RoomPage } from './Room';
import { Data } from '../Model/Data';

export function App() {

  const subjects = useWebSocketSubjects();
  const [data, setData] = useStatePartialUpdate<Data>({
    connected: false,
    subscriptions: {},
    subjects
  });
  const { subscriptions } = data;
  const [eventsCallback, connectionCallback] = useWebSocketCallbacks(data, setData);

  useEffect(() => {
    subscriptions.events?.unsubscribe();
    subscriptions.connection?.unsubscribe();

    const events = subjects.$in.subscribe(eventsCallback);
    const connection = subjects.$connected.subscribe(connectionCallback);

    setData({
      subscriptions: {
        events,
        connection
      }
    });

    return () => {
      subscriptions.events?.unsubscribe();
      subscriptions.connection?.unsubscribe();
    };
  }, [eventsCallback, connectionCallback]);

  return (
    <Switch>
      <Route path='/:roomId'>
        <RoomPage {...data} />
      </Route>
      <Route path='/'>
        <Home {...data} />
      </Route>
    </Switch>);
}
