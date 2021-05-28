import { ResponsiveContainer } from 'recharts';
import { ScatterChart } from './ScatterChart';

export const EffortValueChart = () => {

  return (
    <div style={{
      width: '80vw',
      height: '80vh'
    }}>
      <ResponsiveContainer
        width='100%'
        height='100%'>
        <ScatterChart />
      </ResponsiveContainer>
    </div>);
};
