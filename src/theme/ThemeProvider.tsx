import { ReactNode } from 'react';
import {
    THEME_ID as MATERIAL_THEME_ID,
} from '@mui/material/styles';
import { ThemeProvider } from '@mui/material/styles';
import { CssVarsProvider as JoyCssVarsProvider } from '@mui/joy/styles';
import joyTheme from './joyTheme';
import theme from './theme';

export default function CustomThemeProvider({ children }: { children: ReactNode }) {
    return (
        <ThemeProvider theme={{ [MATERIAL_THEME_ID]: theme }}>
            <JoyCssVarsProvider theme={joyTheme}>
                {children}  
            </JoyCssVarsProvider>
        </ThemeProvider>
    )
}