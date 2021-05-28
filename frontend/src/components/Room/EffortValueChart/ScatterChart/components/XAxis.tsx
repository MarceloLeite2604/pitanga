import { XAxis as RechartXAxis } from 'recharts';
import { axisTicks, domainLimits } from '../../constants';

export const XAxis = () =>
  <RechartXAxis
    dataKey='value'
    type='number'
    axisLine={false}
    hide={false}
    ticks={axisTicks}
    domain={domainLimits} />;
