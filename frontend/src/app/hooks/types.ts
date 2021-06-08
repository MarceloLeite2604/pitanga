import { Dispatch, SetStateAction } from 'react';
import { Data, EventType } from '../../shared/model';

export type EventCallback<T extends EventType> = (event: T) => void;

export type CallbackHookParams = [Data, Dispatch<SetStateAction<Data>>];
