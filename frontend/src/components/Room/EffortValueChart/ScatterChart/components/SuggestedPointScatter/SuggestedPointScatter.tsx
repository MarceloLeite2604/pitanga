import { FC } from 'react';
import { Scatter } from 'recharts';
import { ChartPoint } from '../../../../../../Model';
import { LabelList } from './LabelList';

interface Props {
  suggestedPoint: ChartPoint
}

export const SuggestedPointScatter: FC<Props> = ({ suggestedPoint }) =>
  <Scatter
    data={[suggestedPoint]}
    shape='cross'
    strokeWidth={10}
    strokeOpacity={0.5}
    fillOpacity={0.5}
    fill="#f00">
    <LabelList />
  </Scatter>;
