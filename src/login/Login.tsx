import { useState, useContext } from "react";
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

import styles from './login.module.css';
import { LoginFormData } from "../types";
import { Link } from "react-router-dom";
import { AuthContext } from '../auth/AuthContext';
import { AuthContextType } from '../types';

export default function Login() {
    // Gets the authentification context
    const { login }  = useContext<AuthContextType>(AuthContext);

    // State for shown/hidden password field
    const [showPassword, setShowPassword] = useState<boolean>(false);

    // Keeps the form data (username and password)
    const [formData, setFormData] = useState<LoginFormData>({
        username: '',
        password: '',
    });

    // Handlers for hiding/unhiding password
    const handleClickShowPassword = () => setShowPassword((show) => !show);
    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    // Updates the state of form data
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;
        setFormData({
            ...formData,
            [id]: value,
        });
    };

    // Submits the login form to server
    const handleSubmit = async () => {
        await login(formData.username, formData.password);
    }

    return (
        <Container sx={{ justifyContent: "center", alignItems: "center", display: "flex" }}>
            <Box className={styles.boxContainer}>
                <Box className={styles.header}>
                    <h2 style={{ fontSize: "48px", fontWeight: 700, color: "white" }}>Login</h2>
                </Box>

                <FormControl sx={{ m: 1, width: '25ch' }} variant="outlined" required>
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
                        sx={{ m: 1 }}
                        variant="contained"
                        onClick={handleSubmit}
                    >
                        SUBMIT
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}