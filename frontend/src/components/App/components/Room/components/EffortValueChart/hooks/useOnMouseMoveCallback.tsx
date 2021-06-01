import { useCallback } from 'react';
import { ChartPoint, ChartEventProps, roundChartPoint } from '../../../../../../../Model';
import { useThrottledState } from '../../../../../../../hooks';

export const useOnMouseMoveCallback: () => [ChartPoint | undefined, (props?: ChartEventProps | undefined) => void] = () => {
  const [suggestedPoint, setSuggestedPoint] = useThrottledState<ChartPoint | undefined>(undefined, 30, true);

  const onMouseMoveCallback = useCallback((props?: ChartEventProps) => {
    if (props) {
      const { xValue, yValue } = props;
      setSuggestedPoint(roundChartPoint(xValue, yValue));
    }
  }, [setSuggestedPoint]);

  return [suggestedPoint, onMouseMoveCallback];
};
