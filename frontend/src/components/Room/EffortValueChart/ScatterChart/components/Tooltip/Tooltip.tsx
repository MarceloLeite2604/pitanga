import { FC } from 'react';
import { Tooltip as RechartsTooptip } from 'recharts';
import { ChartPoint } from '../../../../../../Model';
import { TooltipRenderer } from './TooltipRenderer';

interface Props {
  points: ChartPoint[]
}

export const Tooltip: FC<Props> = ({ points }) =>
  <RechartsTooptip content={<TooltipRenderer points={points} />} />;
