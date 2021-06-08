import { Props } from 'recharts/types/component/Label';

export const VotesLabel = ({ x, y, width, value } : Props) => {

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
