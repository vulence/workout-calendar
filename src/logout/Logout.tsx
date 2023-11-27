import React, { useEffect, useContext } from 'react';

import { AuthContext } from '../auth/AuthContext';
import { AuthContextType } from '../types';

export default function Logout() {
    const { logout } = useContext<AuthContextType>(AuthContext);

    useEffect(() => {
        const doLogout = async () => {
            await logout();
        };

        doLogout();
    }, []);

    return null;
}  