import { useCallback } from 'react';
import { ChartPoint } from '../../../../shared/model';

export const useCreateMeanPoint = () => {
  return useCallback((points: ChartPoint[]) => {
    const initialValue = {
      value: 0,
      effort: 0,
      label: 'Mean'
    };
    const mean = points.reduce((acc, point) => {
      acc.effort += point.effort;
      acc.value += point.value;

      return acc;
    }, initialValue);

    mean.value /= points.length;
    mean.effort /= points.length;

    return [mean];
  }, []);
};
