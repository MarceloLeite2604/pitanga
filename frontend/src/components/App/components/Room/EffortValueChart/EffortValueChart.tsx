import { FC } from 'react';
import {
  CartesianGrid,
  LabelList,
  ReferenceLine,
  ResponsiveContainer,
  Scatter,
  ScatterChart,
  Tooltip,
  XAxis,
  YAxis
} from 'recharts';
import { Data } from '../../../../../Model';
import { useOnClickCallback, useOnMouseMoveCallback, usePoints } from './hooks';
import { axisTicks, domainLimits, mediumPoint } from './constants';
import { TooltipRenderer, useStyles, VotesLabel } from './components';
import { useUpdateState } from '../../../../../hooks';

interface Props {
  data: Data
}

/* Recharts does not allow custom component creation... ¯\_(ツ)_/¯ */
export const EffortValueChart: FC<Props> = ({ data }) => {

  const points = usePoints(data);
  const [suggestedPoint, onMouseMoveCallback] = useOnMouseMoveCallback();
  const onClickCallback = useOnClickCallback(data);
  useUpdateState([data.attendee?.vote]);
  const styles = useStyles();

  return (
    <ResponsiveContainer
      aspect={1.6}
      height='100%'
      className={styles.responsiveContainer}>
      <ScatterChart
        width={400}
        height={400}
        margin={{
          top: 40,
          bottom: 40,
          left: 40,
          right: 40
        }}
        onMouseMove={onMouseMoveCallback}
        onClick={onClickCallback}
      >
        <XAxis
          dataKey='value'
          type='number'
          axisLine={false}
          hide={false}
          ticks={axisTicks}
          domain={domainLimits} />
        <YAxis
          dataKey='effort'
          type='number'
          axisLine={false}
          hide={false}
          ticks={axisTicks}
          domain={domainLimits}
        />
        <ReferenceLine
          x={mediumPoint}
          stroke='black'
          label={{
            position: 'insideBottomLeft',
            value: 'Effort'
          }}
        />;
        <ReferenceLine
          y={mediumPoint}
          stroke='black'
          label={{
            position: 'insideBottomLeft',
            value: 'Value'
          }}
        />
        <CartesianGrid strokeDasharray='3 3' />
        <Scatter
          data={points}
          isAnimationActive={false}
          strokeOpacity={1}
          strokeWidth={10}
          fill="#fff">
          <LabelList
            dataKey='label'
            fontSize='10rem'
            content={<VotesLabel />}
          />
        </Scatter>
        {suggestedPoint && <Scatter
          data={[suggestedPoint]}
          shape='cross'
          strokeWidth={10}
          strokeOpacity={0.5}
          fillOpacity={0.5}
          fill="#f00">
        </Scatter>}
        <Tooltip content={<TooltipRenderer points={points} />} />
      </ScatterChart>
    </ResponsiveContainer>
  );
};
