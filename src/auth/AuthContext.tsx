import { createContext, useEffect, useState } from 'react';
import Userfront from '@userfront/toolkit';

import { AuthContextType, AuthProviderProps, User } from '../types';

// Defines the structure of AuthContext
export const AuthContext = createContext<AuthContextType>({
    user: null,
    authenticated: false,
    loading: true,
});

export default function AuthProvider({ children }: AuthProviderProps) {
    // States for whether the user is authenticated and the user data, as well as if the API authentication call is ongoing
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    
    // Checks if the user has a cookie on component mount
    useEffect(() => {
        checkAuthentication();
    }, [])

    const checkAuthentication = () => {
        if (Userfront.tokens.accessToken) {
            setAuthenticated(true);
        }
        setLoading(false);
    };

    return (
        <AuthContext.Provider value={{ user, authenticated, loading }}>
            {children}
        </AuthContext.Provider>
    );
}