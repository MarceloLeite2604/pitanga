import { useEffect, useState } from 'react';
import { Subject } from 'rxjs';
import { w3cwebsocket as W3CWebSocket } from 'websocket';
import { Event } from '../Model';

const SERVER_ADDRESS = 'ws://localhost:8080/pitanga';

function createClient($connected : Subject<boolean>, $incomingEvent : Subject<Event<any>>) {
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
  
    const data = JSON.parse(message.data) as Event<any>;
    $incomingEvent.next(data);
  };

  return client;
}

export interface PitangaWebSocket {
  $connected: Subject<boolean>,
  $incomingEvent: Subject<Event<any>>,
  $outgoingEvent: Subject<Event<any>>,
}

export function usePitangaWebSocket() {
  const [$connected] = useState(new Subject<boolean>());
  const [$incomingEvent] = useState(new Subject<Event<any>>());
  const [$outgoingEvent] = useState(new Subject<Event<any>>());
  const [client] = useState(() => createClient($connected, $incomingEvent));

  useEffect(() => {
    $outgoingEvent.subscribe(event => {
      console.log(`Sending event "${event.type}"`);
      client.send(JSON.stringify(event));
    });
  }, []);

  return {
    $connected,
    $incomingEvent,
    $outgoingEvent
  } as PitangaWebSocket;
};
