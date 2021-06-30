import { render } from 'react-dom';
import { HashRouter } from 'react-router-dom';
import '@fontsource/roboto';
import { ThemeController } from './theme-controller';
import { App } from './app';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

render(
  /*
    Github pages is not too friendly for single page applications. We have to use hash route to allow navigation.
    Looking forward for this issue to be solved: https://github.com/isaacs/github/issues/408
  */
  <HashRouter>
    <ThemeController>
      <App />
    </ThemeController>
    <ToastContainer
      position="bottom-right" />
  </HashRouter>,
  document.getElementById('root')
);
