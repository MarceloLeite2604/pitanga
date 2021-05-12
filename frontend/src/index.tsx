import { StrictMode } from 'react';
import { render } from 'react-dom';
import { App } from './Components';

render(
  <StrictMode>
    <App />
  </StrictMode>,
  document.getElementById('root')
);
