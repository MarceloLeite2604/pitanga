import { useEffect, useState } from 'react';
import { Subject } from 'rxjs';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import configuration from '../../config';
import { Event, WebSocketSubjects, Toast } from '../../shared/model';

function elaborateWebsocketUri() {
  const schema = location.protocol === 'https:' ? 'wss:' : 'ws:';
  const port = location.protocol === 'https:' ? 443 : configuration.port;
  return `${schema}//${configuration.host}:${port}${configuration.path}`;
}

function createClient(webSocketSubjects: WebSocketSubjects) {
  const client = new W3CWebSocket(elaborateWebsocketUri());
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

  const [webSocketSubjects] = useState<WebSocketSubjects>({
    $connected: new Subject<boolean>(),
    $in: new Subject<Event>(),
    $out: new Subject<Event>(),
    $toast: new Subject<Toast>()
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
