import { createTheme } from "@mui/material/";

const theme = createTheme({
    palette: {
        mode: 'dark',
    },
    components: {
        MuiAppBar: {
            styleOverrides: {
                root: {
                    background: 'black',
                    opacity: 0.8,
                },
            },
        },
        MuiDataGrid: {
            styleOverrides: {
                root: {
                    background: 'black',
                    opacity: 0.8,
                    fontSize: 17,
                },
            },
        },
    },
    typography: {
        allVariants: {
            fontFamily: 'Roboto Flex, sans-serif',
            fontSize: 17,
        },
    },
});

export default theme;