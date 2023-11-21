import * as React from 'react';
import { Button } from '@mui/material';
import Container from '@mui/material/Container';

import { AuthContext } from '../auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType } from '../types';

export default function Home() {
    const { logout } = useContext<AuthContextType>(AuthContext);

    const handleLogout = () => {
        logout();
    }
    return (
        <Container>
            <Button onClick={handleLogout}>Logout</Button>
        </Container>
    )
}