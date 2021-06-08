import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import '@fontsource/roboto';
import { ThemeController } from './theme-controller';
import { App } from './app';

render(
  <BrowserRouter>
    <ThemeController>
      <App />
    </ThemeController>
  </BrowserRouter>,
  document.getElementById('root')
);
