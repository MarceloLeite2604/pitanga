import { FC } from 'react';
import { Scatter } from 'recharts';
import { ChartPoint } from '../../../../../../Model';
import { LabelList } from './LabelList';

interface Props {
  points: ChartPoint[]
}

export const VotesScatter: FC<Props> = ({ points }) =>
  <Scatter
    data={points}
    isAnimationActive={false}
    strokeOpacity={1}
    strokeWidth={10}
    fill="#fff">
    <LabelList />
  </Scatter>;
