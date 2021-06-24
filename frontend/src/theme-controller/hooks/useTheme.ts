import { useMemo } from 'react';
import { createMuiTheme } from '@material-ui/core';

export const useTheme = (darkMode: boolean) => {
  return useMemo(() => createMuiTheme({
    palette: {
      type: darkMode ? 'dark' : 'light',
      primary: {
        main: '#9fdc93'
      },
      secondary: {
        main: '#C0A287'
      }
    }
  }), [darkMode]);
};
