import { FC } from 'react';
import { useUpdateState } from '../../hooks';
import { Data } from '../../Model';
import { Attendees } from './Attendees';
import { useEventsCallback } from './hooks/useEventsCallback';

interface Params {
  data: Data
}

export const Room: FC<Params> = ({ data }) => {

  useUpdateState([data.room]);
  useEventsCallback(data);

  return (
    <>
      {
        data.user &&
        data.room &&
        <Attendees
          user={data.user}
          attendees={data.room.attendees} />
      }
    </>
  );
};
