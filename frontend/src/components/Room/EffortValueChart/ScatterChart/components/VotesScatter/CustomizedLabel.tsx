import { FC } from 'react';
import { Props } from 'recharts/types/component/Label';

export const CustomizedLabel: FC<Props> = ({ x, y, width, value }) => {

  if (typeof x !== 'number' ||
    typeof y !== 'number' ||
    typeof width !== 'number') {
    return <></>;
  }

  return (
    <text
      x={x + width / 2}
      y={y + width / 2}
      width={width}
      height={width}
      fill='#fff'
      textAnchor='middle'
      fontSize='1.5rem'
      dominantBaseline='middle'>
      <tspan>
        {value}
      </tspan>
    </text>
  );
};
