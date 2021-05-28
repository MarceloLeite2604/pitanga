import { useEffect, useState } from 'react';
import { Subject } from 'rxjs';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import { Event, WebSocketSubjects } from '../../../Model';
import { useStatePartialUpdate } from '../../../hooks';

const SERVER_ADDRESS = 'ws://localhost:8080/pitanga';

function createClient(webSocketSubjects: WebSocketSubjects) {
  const client = new W3CWebSocket(SERVER_ADDRESS);
  client.onopen = () => {
    webSocketSubjects.$connected.next(true);
  };

  client.onclose = () => {
    webSocketSubjects.$connected.next(false);
  };

  client.onmessage = (message) => {
    if (message.data instanceof Buffer || message.data instanceof ArrayBuffer) {
      throw new Error('Unsuported message data type.');
    }

    const data = JSON.parse(message.data) as Event;
    webSocketSubjects.$in.next(data);
  };

  return client;
}

export const useWebSocketSubjects = () => {

  const [webSocketSubjects] = useStatePartialUpdate<WebSocketSubjects>({
    $connected: new Subject<boolean>(),
    $in: new Subject<Event>(),
    $out: new Subject<Event>()
  });

  const [client] = useState(() => createClient(webSocketSubjects));

  useEffect(() => {
    webSocketSubjects.$out.subscribe(event => {
      console.log(`Sending event "${event.type}"`);
      client.send(JSON.stringify(event));
    });
  }, []);

  return webSocketSubjects;
};
