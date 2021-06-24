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
import { Data, VotingStatus } from '../../../shared/model';
import {
  useCreateMeanPoint,
  useOnClickCallback,
  useOnMouseMoveCallback,
  usePoints,
  useStyles
} from './hooks';
import { axisTicks, domainLimits, mediumPoint } from './constants';
import { useUpdateState } from '../../../shared/hooks';
import { TooltipRenderer } from './TooltipRenderer';
import { VotesLabel } from './VotesLabel';
import { useTheme } from '@material-ui/core';

interface Props {
  data: Data
}

/* Recharts does not allow custom component creation... ¯\_(ツ)_/¯ */
export const EffortValueChart = ({ data }: Props) => {

  const theme = useTheme();
  const points = usePoints(data);
  const [suggestedPoint, onMouseMoveCallback] = useOnMouseMoveCallback();
  const onClickCallback = useOnClickCallback(data);
  useUpdateState([data.attendee?.vote]);
  const styles = useStyles();
  const createMeanPoint = useCreateMeanPoint();

  return (
    <ResponsiveContainer
      aspect={1.6}
      width='100%'
      className={styles.responsiveContainer}>
      <ScatterChart
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
          domain={domainLimits}
          stroke={theme.palette.primary.dark} />
        <YAxis
          dataKey='effort'
          type='number'
          axisLine={false}
          hide={false}
          ticks={axisTicks}
          domain={domainLimits}
          stroke={theme.palette.primary.dark} />
        <ReferenceLine
          x={mediumPoint}
          stroke={theme.palette.primary.dark}
          strokeWidth={2}
          label={{
            position: 'insideBottomLeft',
            value: 'Effort',
            fill: theme.palette.primary.dark
          }}
        />;
        <ReferenceLine
          y={mediumPoint}
          stroke={theme.palette.primary.dark}
          strokeWidth={2}
          label={{
            position: 'insideBottomLeft',
            value: 'Value',
            fill: theme.palette.primary.dark
          }}
        />
        <CartesianGrid
          strokeDasharray='2 4'
          stroke={theme.palette.grey[400]} />
        <Scatter
          data={points}
          isAnimationActive={false}
          strokeOpacity={1}
          strokeWidth={10}
          fill="#fff">
          <LabelList
            dataKey='label'
            fontSize='4rem'
            content={<VotesLabel />}
          />
        </Scatter>
        {data.room?.votingStatus === VotingStatus.Open &&
          suggestedPoint &&
          <Scatter
            data={[suggestedPoint]}
            shape='cross'
            rotate={200}
            strokeWidth={12}
            strokeOpacity={0.5}
            fillOpacity={1}
            fill={theme.palette.secondary.dark}>
          </Scatter>}
        {data.room?.votingStatus === VotingStatus.Closed &&
          <Scatter
            data={createMeanPoint(points)}
            shape='cross'
            rotate={200}
            strokeWidth={12}
            strokeOpacity={0.5}
            fillOpacity={1}
            fill={theme.palette.primary.dark}>
          </Scatter>}
        <Tooltip content={<TooltipRenderer points={points} />} />
      </ScatterChart>
    </ResponsiveContainer>
  );
};
