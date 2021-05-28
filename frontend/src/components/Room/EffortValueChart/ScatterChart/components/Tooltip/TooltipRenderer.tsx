import { FC } from 'react';
import { TooltipProps } from 'recharts';
import { NameType, ValueType } from 'recharts/types/component/DefaultTooltipContent';
import { ChartPoint } from '../../../../../../Model';

interface ExtraProps {
  points: ChartPoint[]
};

type Props = TooltipProps<ValueType, NameType> & ExtraProps;

export const TooltipRenderer: FC<Props> = ({ active, payload, points }) => {
  if (active && payload && payload[0]) {
    const hoveredPoint = payload[0].payload as ChartPoint;

    const hoveredLabels = points.filter(point =>
      point.effort === hoveredPoint.effort &&
      point.value === hoveredPoint.value)
      .map(point => point.label);

    if (hoveredLabels && hoveredLabels.length > 1) {

      const itemsPerLine = Math.round(Math.sqrt(hoveredLabels.length));
      const tooltipContent = hoveredLabels.reduce((acc, label, index) => {
        acc += label;
        if (index % itemsPerLine === 0) {
          acc += '\n';
        }
        return acc;
      });

      return <div style={{
        backgroundColor: '#0008',
        fontSize: '0.8rem',
        letterSpacing: '0.5rem',
        padding: '0.5rem 0rem 0.5rem 0.5rem',
        borderRadius: '0.1rem',
        whiteSpace: 'pre-wrap'
      }}>
        {tooltipContent}
      </div>;
    }
  }
  return <></>;
};
