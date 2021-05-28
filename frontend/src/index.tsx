import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { App } from './components';
import { ThemeController } from './components/ThemeController/ThemeController';
import '@fontsource/roboto';

render(
  <BrowserRouter>
    <ThemeController>
      <App />
    </ThemeController>
  </BrowserRouter>,
  document.getElementById('root')
);
