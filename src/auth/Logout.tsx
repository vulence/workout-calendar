import { useEffect } from 'react';

import Userfront from '@userfront/toolkit';

export default function Logout() {
    useEffect(() => {
        Userfront.logout();
    }, []);

    return null;
}  