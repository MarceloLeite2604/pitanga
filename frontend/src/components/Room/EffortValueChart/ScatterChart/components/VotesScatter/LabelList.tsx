import { LabelList as RechartsLabelList } from 'recharts';
import { CustomizedLabel } from './CustomizedLabel';

export const LabelList = () =>
  <RechartsLabelList
    dataKey='label'
    fontSize='10rem'
    content={<CustomizedLabel />}
  />;
