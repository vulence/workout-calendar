import { createTheme } from "@mui/material/styles";

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
        MuiDialog: {
            styleOverrides: {
                paper: {
                    background: "rgba(40,40,40,1)",
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
    cssVariables: true
});

export default theme;