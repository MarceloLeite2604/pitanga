import { useCallback, useState } from 'react';
import { CartesianGrid, ScatterChart as RechartsScatterChart } from 'recharts';
import { ChartEventProps, ChartPoint, roundChartPoint } from '../../../../Model';
import { useOnMouseMoveCallback } from '../hooks';
import {
  EffortReferenceLine,
  SuggestedPointScatter,
  Tooltip,
  ValueReferenceLine,
  VotesScatter,
  XAxis,
  YAxis
} from './components';

export const ScatterChart = () => {
  const [points, setPoints] = useState<ChartPoint[]>([]);
  const [suggestedPoint, onMouseMoveCallback] = useOnMouseMoveCallback();

  const onClickCallback = useCallback((props: ChartEventProps) => {
    const { xValue, yValue } = props;
    const point = roundChartPoint(xValue, yValue, points[0].label);
    setPoints(previousState => [point, ...previousState.slice(1)]);
  }, [setPoints]);

  return (
    <RechartsScatterChart
      width={600}
      height={600}
      margin={{
        top: 40,
        bottom: 40,
        left: 40,
        right: 40
      }}
      onMouseMove={onMouseMoveCallback}
      onClick={onClickCallback}
    >
      <XAxis />
      <YAxis />
      <EffortReferenceLine />
      <ValueReferenceLine />
      <CartesianGrid strokeDasharray='3 3' />
      <VotesScatter points={points} />
      {suggestedPoint && <SuggestedPointScatter suggestedPoint={suggestedPoint} />}
      <Tooltip points={points} />
    </RechartsScatterChart>
  );
};
