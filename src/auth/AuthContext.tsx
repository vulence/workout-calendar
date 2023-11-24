import { createContext, useEffect, useState } from 'react';

import { AuthContextType, AuthProviderProps, User } from '../types';

// Defines the structure of AuthContext
export const AuthContext = createContext<AuthContextType>({
    user: null,
    authenticated: false,
    login: async () => { },
    logout: () => {},
    loading: true,
});

export default function AuthProvider({ children }: AuthProviderProps) {
    // States for whether the user is authenticated and the user data, as well as if the API authentication call is ongoing
    const [authenticated, setAuthenticated] = useState<boolean>(false);
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState<boolean>(true);

    // Logs the user in
    const login = async (username: string, password: string) => {
        try {
            const response = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ username, password }),
                credentials: "include"
            });

            const result = await response.json();

            // If response status is 201, then store the user object and set authentication to true
            if (response.ok) {
                setUser(result);
                setAuthenticated(true);
            }
            else {
                console.error('Login failed!');
            }
        }
        catch (error) {
            console.error(error);
        }
    }

    // Logs the user out
    const logout = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/users/logout', {
                method: 'POST',
                credentials: 'include',
            });

            const result = await response.json();

            if (response.ok) {
                setUser(null);
                setAuthenticated(false);
            }
            else {
                console.error("Logout failed!");
            }
        }
        catch (error) {
            console.error(error);
        }
    }

    const checkAuthentication = async () => {
        // Checks if the user already has a cookie
        try {
            const response = await fetch('http://localhost:8080/api/users/check-authentication', {
                method: "GET",
                headers: {
                    'Content-Type': "application/json",
                },  
                credentials: 'include',
            });

            const result = await response.json();

            // If he does, set the user object
            if (response.ok) {
                setUser(result);
                setAuthenticated(true);
            }
            else {
                setAuthenticated(false);
            }
        }
        catch (error) {
            console.error(error);
        }
        finally {
            setLoading(false);
        }
    };

    // Checks if the user has a cookie on component mount
    useEffect(() => {
        checkAuthentication();
    }, [])

    return (
        <AuthContext.Provider value={{ user, authenticated, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
}