export interface ChartPoint {
  value: number,
  effort: number,
  label: string,
}

export const createChartPoint = (x: number, y: number, label?: string) => ({
  value: Math.round(x),
  effort: Math.round(y),
  label
} as ChartPoint);
