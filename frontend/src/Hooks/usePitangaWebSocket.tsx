import { useEffect, useState } from 'react';
import { Subject } from 'rxjs';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import { OutgoingEvent, IncomingEvent } from '../Model/Events';

const SERVER_ADDRESS = 'ws://localhost:8080/pitanga';

function createClient($connected : Subject<boolean>, $incomingEvent : Subject<IncomingEvent<any>>) {
  const client = new W3CWebSocket(SERVER_ADDRESS);
  client.onopen = () => {
    $connected.next(true);
  };

  client.onclose = () => {
    $connected.next(false);
  };

  client.onmessage = (message) => {
    if (message.data instanceof Buffer || message.data instanceof ArrayBuffer) {
      throw new Error('Unsuported message data type.');
    }
  
    const data = JSON.parse(message.data) as IncomingEvent<any>;
    $incomingEvent.next(data);
  };

  return client;
}

export function usePitangaWebSocket() {
  const [$connected] = useState(new Subject<boolean>());
  const [$incomingEvent] = useState(new Subject<IncomingEvent<any>>());
  const [$outgoingEvent] = useState(new Subject<OutgoingEvent<any>>());
  const [client] = useState(createClient($connected, $incomingEvent));

  useEffect(() => {
    $outgoingEvent.subscribe(data => {
      client.send(JSON.stringify(data));
    });
  }, []);

  return {
    $connected,
    $incomingEvent,
    $outgoingEvent
  };
};
