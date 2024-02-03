import { Box, Typography } from "@mui/material";

export default function Footer() {

    return (
        <Box sx={{ width: "100%", bottom: "0", position: "relative"}}>
            <Typography sx={{color: "grey", padding: "10px"}}>Copyright © 2024 Rise&Grind</Typography>
        </Box>
    );
}