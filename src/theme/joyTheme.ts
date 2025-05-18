import { extendTheme } from "@mui/joy/styles";
import { accordionDetailsClasses } from "@mui/joy";

const joyTheme = extendTheme({
    components: {
        JoyAccordion: {
            styleOverrides: {
                root: {
                    backgroundColor: "black",
                },
            }
        },
        JoyAccordionSummary: {
            styleOverrides: {
                button: {
                    '&:hover': {
                        backgroundColor: "#535454 !important",
                    },
                    backgroundColor: "black",
                    paddingBlock: "1rem",
                    borderRadius: "20px"
                },
            }
        },
        JoyAccordionDetails: {
            styleOverrides: {
                content: {
                    paddingBlock: "1rem",
                },
                root: {
                    [`&.${accordionDetailsClasses.expanded}`]: {
                        paddingBlock: "1rem"
                    },
                }
            }
        },
        JoyCard: {
            styleOverrides: {
                root: {
                    backgroundColor: "black"
                }
            }
        },
        JoyCardOverflow: {
            styleOverrides: {
                root: {
                    backgroundColor: "black"
                }
            }
        }
    },
});

export default joyTheme;