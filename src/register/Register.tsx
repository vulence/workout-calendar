import React, { useContext } from 'react';
import { useState } from 'react';
import { Box, Button, Container, FormHelperText, TextField, OutlinedInput, InputLabel, InputAdornment, FormControl, IconButton } from '@mui/material';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import AccountCircle from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';

import styles from './register.module.css';
import { AuthContextType, RegisterFormData } from '../types';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from '../auth/AuthContext';

export default function Register() {
    // Gets the authentification context
    const { register } = useContext<AuthContextType>(AuthContext);

    // State for shown/hidden password field
    const [showPassword, setShowPassword] = useState<boolean>(false);

    // Form validation states
    const [usernameValidText, setUsernameValidText] = useState<string | null>(null);
    const [emailValidText, setEmailValidText] = useState<string | null>(null);

    // Allows navigation between different routes
    let navigate = useNavigate();

    // Registration form data
    const [formData, setFormData] = useState<RegisterFormData>({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

    // Handlers for hiding/unhiding password
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    // Updates the state of formdata
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;

        if (id === 'email') setEmailValidText(null);
        if (id === 'username') setUsernameValidText(null);

        setFormData({
            ...formData,
            [id]: value,
        });
    }

    // Submits the registration form to server
    const handleSubmit = async () => {
        const result = await register(formData.username, formData.email, formData.password);

        if (result === "User created") {
            navigate("/login");
        }
        else {
            displayErrorHelperText(result);
        }
    }

    // Displays the error helper text if the registration data is invalid
    const displayErrorHelperText = (errorMessage : string) => {
        if (errorMessage === "Username is already taken") {
            setUsernameValidText(errorMessage);
        }
        else if (errorMessage === "Email is already taken") {
            setEmailValidText(errorMessage);
        }
    }

    return (
        <Container sx={{ justifyContent: "center", alignItems: "center", display: "flex" }}>
            <Box className={styles.boxContainer}>
                <Box className={styles.header}>
                    <h2 style={{ fontSize: "48px", fontWeight: 700, color: "white" }}>Register</h2>
                </Box>

                <FormControl sx={{m: 1, width: '25ch'}} variant="outlined" required error={usernameValidText ? true : false}>
                    <InputLabel htmlFor="username">
                        Username
                    </InputLabel>
                    <OutlinedInput
                        id="username"
                        startAdornment={
                            <InputAdornment position="start">
                                <AccountCircle />
                            </InputAdornment>
                        }
                        label="Username"
                        value={formData.username}
                        onChange={(e) => handleChange(e)}
                    />
                    <FormHelperText id="username-helper-text" error={usernameValidText ? true : false}>{usernameValidText}</FormHelperText>
                </FormControl>

                <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined" required error={emailValidText ? true : false}>
                    <InputLabel htmlFor="email">
                        Email
                    </InputLabel>
                    <OutlinedInput
                        id="email"
                        startAdornment={
                            <InputAdornment position="start">
                                <EmailIcon />
                            </InputAdornment>
                        }
                        label="Email"
                        value={formData.email}
                        onChange={(e) => handleChange(e)}
                    />
                    <FormHelperText id="email-helper-text" error={emailValidText ? true : false}>{emailValidText}</FormHelperText>
                </FormControl>

                <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined" required>
                    <InputLabel htmlFor="password">Password</InputLabel>
                    <OutlinedInput
                        id="password"
                        type={showPassword ? 'text' : 'password'}
                        endAdornment={
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={handleClickShowPassword}
                                    onMouseDown={handleMouseDownPassword}
                                    edge="end"
                                >
                                    {showPassword ? <VisibilityOff /> : <Visibility />}
                                </IconButton>
                            </InputAdornment>
                        }
                        label="Password"
                        value={formData.password}
                        onChange={(e) => handleChange(e)}
                    />
                </FormControl>
                <Button
                    sx={{ m: 1}}
                    variant="contained"
                    onClick={handleSubmit}
                >
                    SUBMIT
                </Button>
            </Box>
        </Container>
    );
}