import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import { App } from './Components';
import { ThemeController } from './Components/ThemeController/ThemeController';
import '@fontsource/roboto';

render(
  <BrowserRouter>
    <ThemeController>
      <App />
    </ThemeController>
  </BrowserRouter>,
  document.getElementById('root')
);
