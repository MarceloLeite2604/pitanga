import { FC, useEffect } from 'react';
import { useHistory } from 'react-router';
import { PitangaWebSocket } from '../../Hooks';
import { Room as RoomModel, User, buildCheckRoomExists } from '../../Model';

interface Props {
  user?: User,
  room?: RoomModel,
  pitangaWebSocket: PitangaWebSocket
}

export const Room: FC<Props> = ({ user, room, pitangaWebSocket }) => {

  const history = useHistory();

  useEffect(() => {
    if (!room) {
      history.push('/');
    }
    room && pitangaWebSocket.$outgoingEvent.next(buildCheckRoomExists(room));
  });
  return (<p>{`Welcome user ${user?.id} to room #${room?.id}.`}</p>);
};
