import { FC } from 'react';
import { PitangaWebSocket } from '../../Hooks';
import { Room as RoomModel, User } from '../../Model';

interface Props {
  connected: boolean,
  user: User,
  pitangaWebSocket: PitangaWebSocket
  room: RoomModel
}

export const Room: FC<Props> = ({connected, user, pitangaWebSocket, room}) => {
  return (<></>);
};
