import { useState, useContext } from "react";
import { Box, Button, Container, FormHelperText, OutlinedInput, InputLabel, InputAdornment, FormControl, IconButton } from '@mui/material';
import { Link } from "react-router-dom";
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import AccountCircle from '@mui/icons-material/AccountCircle';
import Userfront from "@userfront/toolkit";

import formStyles from './form.module.css';
import { LoginFormData } from "../types";

Userfront.init("7n84856n");

export default function Login() {
    // State for shown/hidden password field
    const [showPassword, setShowPassword] = useState<boolean>(false);

    // Keeps the form data (username and password)
    const [formData, setFormData] = useState<LoginFormData>({
        emailOrUsername: '',
        password: '',
    });

    // Form validation states
    const [loginValidText, setLoginValidText] = useState<string | null>(null);

    // Handlers for hiding/unhiding password
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    // Updates the state of form data
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;

        setLoginValidText(null);

        setFormData({
            ...formData,
            [id]: value,
        });
    };

    // Submits the login form to server
    const handleSubmit = async (e : React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault();
        
        Userfront.login({
            method: "password",
            emailOrUsername: formData.emailOrUsername,
            password: formData.password
        })
        .catch((error) => setLoginValidText(error.message))
    }

    return (
        <Container className={formStyles.content}>
            <Box className={formStyles.boxContainer} component="form" onSubmit={handleSubmit}>
                <Box className={formStyles.header}>
                    <h2 className={formStyles.h2}>Login</h2>
                </Box>

                <FormControl className={formStyles.formElement} variant="outlined" required error={loginValidText ? true : false}>
                    <InputLabel htmlFor="emailOrUsername">
                        Email or username
                    </InputLabel>
                    <OutlinedInput
                        id="emailOrUsername"
                        startAdornment={
                            <InputAdornment position="start">
                                <AccountCircle />
                            </InputAdornment>
                        }
                        label="email or username"
                        value={formData.emailOrUsername}
                        onChange={(e) => handleChange(e)}
                    />
                </FormControl>

                <FormControl className={formStyles.formElement} variant="outlined" required error={loginValidText ? true : false}>
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
                    <FormHelperText id="username-helper-text" error={loginValidText ? true : false}>{loginValidText}</FormHelperText>
                </FormControl>

                <Box sx={{display: 'flex', flexDirection: "row"}}>
                    <Link to="/register">
                        <Button
                            sx={{marginRight: "30px", height:"100%"}}
                            variant="text"
                        >
                            Create account
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