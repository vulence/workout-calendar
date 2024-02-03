import { Box, Typography, Button, Stack } from "@mui/material";
import styles from "./guestHome.module.css";
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import TimelineIcon from '@mui/icons-material/Timeline';
import RestaurantIcon from '@mui/icons-material/Restaurant';

export default function GuestHome() {
    return (
        <Box className={styles.container}>
            <Box className={styles.introduction}>
                <Typography className={styles.introductionHeader}>Welcome Rise & Grind, the only fitness companion you need.</Typography>
                <Typography className={styles.introductionText}>App used by millions worldwide. Become part of the tradition.</Typography>
                <Button variant="contained" className={styles.getStartedButton}>GET STARTED</Button>
            </Box>

            <Box className={styles.features}>
                <Box className={styles.featureItems}>
                    <Typography className={styles.featuresHeader}>What's in it for you?</Typography>
                    <Box className={styles.featureContainer}>
                        <EventAvailableIcon className={styles.featureIcon} />
                        <Stack>
                            <Typography variant="h1" sx={{color: "white", alignSelf: "flex-start", fontWeight: "bold", marginBottom: "10px"}}>Interactive calendar</Typography>
                            <Typography align="left" sx={{ color: "white" }}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>

                    <Box className={styles.featureContainer}>
                        <TimelineIcon className={styles.featureIcon} />
                        <Stack>
                            <Typography variant="h1" sx={{color: "white", alignSelf: "flex-start", fontWeight: "bold", marginBottom: "10px"}}>Visual history</Typography>
                            <Typography align="left" sx={{ color: "white" }}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>

                    <Box className={styles.featureContainer}>
                        <RestaurantIcon className={styles.featureIcon} />
                        <Stack>
                            <Typography variant="h1" sx={{color: "white", alignSelf: "flex-start", fontWeight: "bold", marginBottom: "10px"}}>Meal tracker</Typography>
                            <Typography align="left" sx={{ color: "white" }}>Aliquam tempus ligula non turpis ornare tempus. Maecenas euismod quam vitae venenatis tristique.</Typography>
                        </Stack>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
}