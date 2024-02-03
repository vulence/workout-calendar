import { Box, Typography, Button, Stack } from "@mui/material";
import styles from "./guestHome.module.css";
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import TimelineIcon from '@mui/icons-material/Timeline';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import pcImg from '../assets/macbook-47617-overlay.png';
import { useNavigate } from "react-router-dom";

export default function GuestHome() {
    const navigate = useNavigate();

    return (
        <Box className={styles.container}>
            <Box className={styles.introduction}>
                <Typography className={styles.introductionHeader}>Welcome to Rise & Grind, the only fitness companion you need.</Typography>
                <Typography className={styles.introductionText}>App used by millions worldwide. Become part of the tradition.</Typography>
                <Button variant="contained" className={styles.getStartedButton} onClick={() => navigate("/register")}>GET STARTED</Button>
            </Box>

            <Box className={styles.features}>
                <Box className={styles.featureItems}>
                    <Typography className={styles.featuresHeader}>What's in it for you?</Typography>

                    <Box className={styles.featureContainer}>
                        <EventAvailableIcon className={styles.featureIcon} color="primary" />
                        <Stack>
                            <Typography variant="h1" className={styles.featureName}>Interactive calendar</Typography>
                            <Typography align="left" className={styles.featureDescription}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>

                    <Box className={styles.featureContainer}>
                        <TimelineIcon className={styles.featureIcon} color="secondary" />
                        <Stack>
                            <Typography variant="h1" className={styles.featureName}>Visual history</Typography>
                            <Typography align="left" className={styles.featureDescription}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>

                    <Box className={styles.featureContainer}>
                        <RestaurantIcon className={styles.featureIcon} color="success" />
                        <Stack>
                            <Typography variant="h1" className={styles.featureName}>Meal tracker</Typography>
                            <Typography align="left" className={styles.featureDescription}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>
                </Box>

                <img src={pcImg} alt="PC Image" className={styles.image} />
            </Box>
        </Box>
    );
}