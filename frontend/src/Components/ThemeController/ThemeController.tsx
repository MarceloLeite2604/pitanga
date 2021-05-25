import { FC } from 'react';
import { ThemeProvider, useMediaQuery, CssBaseline } from '@material-ui/core';
import { useTheme } from './hooks';

export const ThemeController: FC = ({ children }) => {
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');
  const theme = useTheme(prefersDarkMode);

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </ThemeProvider>
  );
};
