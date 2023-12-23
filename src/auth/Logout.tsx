import { useEffect } from 'react';
import Userfront from '@userfront/toolkit';

import { Box } from '@mui/material';
import styles from './form.module.css';

export default function Logout() {
    useEffect(() => {
        Userfront.logout();
    }, []);

    return (
        <Box>
            <h2 className={styles.h2}>You're being logged out..</h2>
        </Box>
    );
}