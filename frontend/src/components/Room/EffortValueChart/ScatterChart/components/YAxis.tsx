import { YAxis as RechartYAxis } from 'recharts';
import { axisTicks, domainLimits } from './constants';

export const YAxis = () =>
  <RechartYAxis
    dataKey='effort'
    type='number'
    axisLine={false}
    hide={false}
    ticks={axisTicks}
    domain={domainLimits}
  />;
