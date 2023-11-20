import React from 'react';
import { useState } from 'react';
import { Box, Button, Container, TextField } from '@mui/material';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import AccountCircle from '@mui/icons-material/AccountCircle';
import EmailIcon from '@mui/icons-material/Email';

import styles from './register.module.css';
import { RegisterFormData } from '../types';

export default function Register() {
    const [showPassword, setShowPassword] = useState<boolean>(false);

    const [formData, setFormData] = useState<RegisterFormData>({
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
    });

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;
        setFormData({
            ...formData,
            [id]: value,
        });
    }

    const handleSubmit = async () => {
        try {
            let username : string = formData.username;
            let email : string = formData.email;
            let password : string = formData.password;

            const response = await fetch("http://localhost:8080/api/users/register", {
                method: "POST",
                headers: {
                    'Content-Type': "application/json",
                },
                body: JSON.stringify({ username, email, password })
            });

            const result = await response.text();
            console.log(result);
        }
        catch (error) {
            console.error(error);
        }
    }

    return (
        <Container sx={{ justifyContent: "center", alignItems: "center", display: "flex" }}>
            <Box className={styles.boxContainer}>
                <Box className={styles.header}>
                    <h2 style={{ fontSize: "48px", fontWeight: 700, color: "white" }}>Register</h2>
                </Box>

                <FormControl sx={{m: 1, width: '25ch'}} variant="outlined" required>
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
                </FormControl>

                <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined" required>
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