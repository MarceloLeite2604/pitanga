import { render } from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import '@fontsource/roboto';
import { ThemeController } from './theme-controller';
import { App } from './app';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

render(
  <BrowserRouter>
    <ThemeController>
      <App />
    </ThemeController>
    <ToastContainer
      position="bottom-right" />
  </BrowserRouter>,
  document.getElementById('root')
);
