import { createContext, useEffect, useState } from 'react';

import { AuthContextType, AuthProviderProps } from '../types';

// Defines the structure of AuthContext
export const AuthContext = createContext<AuthContextType>({
    token: null,
    login: () => {},
    logout: () => {},
});

export default function AuthProvider({ children }: AuthProviderProps) {
    // Sets the initial JWT state to the token value
    const [token, setToken] = useState<string | null>(localStorage.getItem('token'));

    // Logs the user in, setting the token in context and local storage
    const login = (newToken : string) => {
        setToken(newToken);
        localStorage.setItem('token', newToken);
    }

    // Logs the user out, removing the token from context and local storage
    const logout = () => {
        setToken(null);
        localStorage.removeItem('token');
    }

    // Checks if the user has a token on component mount
    useEffect(() => {
        const storedToken = localStorage.getItem('token');
        if (storedToken) setToken(storedToken);
    }, [])

    return (
        <AuthContext.Provider value={{ token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}