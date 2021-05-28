export interface ChartPoint {
  value: number,
  effort: number,
  label: string
}

export const roundChartPoint = (x: number, y: number, label?: string) => {
  return {
    value: Math.round(x),
    effort: Math.round(y),
    label
  } as ChartPoint;
};
