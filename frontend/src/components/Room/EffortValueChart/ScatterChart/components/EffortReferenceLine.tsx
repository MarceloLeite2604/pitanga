import { ReferenceLine } from 'recharts';
import { mediumPoint } from '../../constants';

export const EffortReferenceLine = () =>
  <ReferenceLine
    x={mediumPoint}
    stroke='black'
    label={{
      position: 'insideBottomLeft',
      value: 'Effort'
    }}
  />;
