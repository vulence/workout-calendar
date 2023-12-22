import React from 'react';
import { useState } from 'react';
import { Box, Button, Container, FormHelperText, OutlinedInput, InputLabel, InputAdornment, FormControl, IconButton } from '@mui/material';
import { Link } from 'react-router-dom';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import AccountCircle from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';
import Userfront from '@userfront/toolkit';

import styles from './register.module.css';
import formStyles from './form.module.css';
import { RegisterFormData } from '../types';

Userfront.init("7n84856n");

export default function Register() {
    // State for shown/hidden password field
    const [showPassword, setShowPassword] = useState<boolean>(false);

    // Registration form data
    const [formData, setFormData] = useState<RegisterFormData>({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

    // Error text for registration form data
    const [errorHelperText, setErrorHelperText] = useState<RegisterFormData>({
        username: '',
        email: '',
        password: '',
        confirmPassword: ''
    });

    // Handlers for hiding/unhiding password
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    // Updates the state of formdata
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;

        Object.keys(errorHelperText).forEach((key) => {
            setErrorHelperText(prevState => ({
                ...prevState,
                [key]: ''
            }));
        });

        setFormData({
            ...formData,
            [id]: value,
        });
    }

    // Submits the registration form to server
    const handleSubmit = async (e : React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();

        Userfront.signup({
            method: "password",
            email: formData.email,
            username: formData.username,
            password: formData.password,
        })
        .catch(error => displayErrorHelperText(error.message));
    }

    // Displays the error helper text if the registration data is invalid
    const displayErrorHelperText = (errorMessage: string) => {
        if (errorMessage.toLowerCase().includes("username")) {
            setErrorHelperText({
                ...errorHelperText,
                username: errorMessage,
            });
        }
        else if (errorMessage.toLowerCase().includes("email")) {
            setErrorHelperText({
                ...errorHelperText,
                email: errorMessage,
            })
        }
        else {
            setErrorHelperText({
                ...errorHelperText,
                password: errorMessage,
            })
        }
    }

    return (
        <Container className={formStyles.content}>
            <Box className={formStyles.boxContainer} component="form" onSubmit={handleSubmit}>
                <Box className={formStyles.header}>
                    <h2 className={formStyles.h2}>Register</h2>
                </Box>

                <FormControl className={formStyles.formElement} variant="outlined" required error={errorHelperText.username !== '' ? true : false}>
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
                    <FormHelperText id="username-helper-text" error={errorHelperText.username !== '' ? true : false}>{errorHelperText.username}</FormHelperText>
                </FormControl>

                <FormControl className={formStyles.formElement} variant="outlined" required error={errorHelperText.email !== '' ? true : false}>
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
                    <FormHelperText id="email-helper-text" error={errorHelperText.email !== '' ? true : false}>{errorHelperText.email}</FormHelperText>
                </FormControl>

                <FormControl className={formStyles.formElement} variant="outlined" required error={errorHelperText.password !== '' ? true : false}>
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
                    <FormHelperText id="password-helper-text" error={errorHelperText.password ? true : false}>{errorHelperText.password}</FormHelperText>
                </FormControl>

                <Box sx={{ display: 'flex', flexDirection: "row" }}>
                    <Link to="/login">
                        <Button
                            sx={{ marginRight: "30px", height: "100%" }}
                            variant="text"
                        >
                            Go to login
                        </Button>
                    </Link>

                    <Button
                        type="submit"
                        sx={{ m: 1 }}
                        variant="contained"
                    >
                        SUBMIT
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}