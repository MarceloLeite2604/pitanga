import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { App, ThemeController } from './components';
import '@fontsource/roboto';

render(
  <BrowserRouter>
    <ThemeController>
      <App />
    </ThemeController>
  </BrowserRouter>,
  document.getElementById('root')
);
