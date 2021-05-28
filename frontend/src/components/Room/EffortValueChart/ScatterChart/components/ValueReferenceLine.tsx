import { ReferenceLine } from 'recharts';
import { mediumPoint } from '../../constants';

export const ValueReferenceLine = () =>
  <ReferenceLine
    y={mediumPoint}
    stroke='black'
    label={{
      position: 'insideBottomLeft',
      value: 'Value'
    }}
  />;
