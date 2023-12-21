import { Box, Container } from "@mui/material";
import Userfront, { LoginForm, LogoutButton } from "@userfront/toolkit";
import { useEffect } from "react";

Userfront.init("7n84856n");

export default function UserfrontLogin() {
    return (
        <Container>
            <Box sx={{margin: 5}}>
                <LoginForm />
            </Box>
        </Container>
    );
}