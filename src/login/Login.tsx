import { useNavigate, useLocation } from "react-router";
import { useState, useContext } from "react";
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TextField from '@mui/material/TextField';

import { FormData } from "../types";

export default function Login() {
    localStorage.setItem('token', '');

    const [formData, setFormData] = useState<FormData>({
        username: '',
        password: '',
    });

    const handleChange = (e : React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target;
        setFormData({
            ...formData,
            [id]: value,
        });
    };

    const handleSubmit = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData),
            });

            if (response.ok) {
                const token = await response.text();
                localStorage.setItem('token', token);
                console.log(token);
            }
            else {
                console.error('Login failed!');
            }
        }
        catch (error) {
            console.error(error);
        }
    }

    return (
        <Container>
            <h2>Login</h2>
            <TextField
                id="username"
                label="Username"
                value={formData.username}
                onChange={handleChange}
            />
            <TextField
                id="password"
                label="Password"
                value={formData.password}
                onChange={handleChange}
            />
            <Button onClick={handleSubmit}>Submit</Button>
        </Container>
    );
}