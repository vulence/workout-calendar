import { Box, Container } from "@mui/material";
import Userfront, { SignupForm } from "@userfront/toolkit";

Userfront.init("7n84856n");

export default function UserfrontLogin() {
    return (
        <Container>
            <Box sx={{margin: 5}}>
                <SignupForm />
            </Box>
        </Container>
    );
}