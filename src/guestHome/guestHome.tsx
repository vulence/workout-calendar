import { Box, Typography, Button, Stack, Card, CardContent, Avatar, IconButton, Rating } from "@mui/material";
import { useState, useEffect } from 'react';
import styles from "./guestHome.module.css";
import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import TimelineIcon from '@mui/icons-material/Timeline';
import RestaurantIcon from '@mui/icons-material/Restaurant';
import pcImg from '../assets/macbook-47617-overlay.png';
import KeyboardArrowLeftIcon from '@mui/icons-material/KeyboardArrowLeft';
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight';
import { useNavigate } from "react-router-dom";
import { useSpring, animated } from "@react-spring/web";

export default function GuestHome() {
    const navigate = useNavigate();

    const [currentTestimonial, setCurrentTestimonial] = useState<number>(0);
    // Tracks if the testimonials scroll to the left or right, depending on the pressed button
    const [testimonialDirection, setTestimonialDirection] = useState<"left" | "right">("right");

    const testimonials = [
        {
            name: "David Tom",
            photo: "",
            quote: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque aliquet nec tellus quis posuere. Ut auctor velit ac diam egestas.",
            rating: 5,
        },
        {
            name: "John Doe",
            photo: "",
            quote: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque aliquet nec tellus quis posuere. Ut auctor velit ac diam egestas.",
            rating: 4,
        },
        {
            name: "Jack Jones",
            photo: "",
            quote: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque aliquet nec tellus quis posuere. Ut auctor velit ac diam egestas.",
            rating: 5
        }
    ];

    const springProps = useSpring({
        opacity: 1,
        transform: `translateX(0%)`,
        from: { opacity: 0, transform: `translateX(${testimonialDirection === 'left' ? '' : '-'}150%)` },
        reset: true,
    });

    useEffect(() => {
        const timeout = setTimeout(() => {
            setCurrentTestimonial((prevIndex) => (prevIndex + 1) % testimonials.length);
        }, 3000);

        return () => clearTimeout(timeout);
    }, [currentTestimonial]);

    const handlePrevClick = () => {
        setTestimonialDirection("left");
        setCurrentTestimonial((prevIndex) => (prevIndex - 1 + testimonials.length) % testimonials.length);
    };

    const handleNextClick = () => {
        setTestimonialDirection("right");
        setCurrentTestimonial((prevIndex) => (prevIndex + 1) % testimonials.length);
    };

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

            <Box className={styles.testimonials}>
                <animated.div style={{ display: "flex", alignItems: "center", justifyContent: "center", ...springProps }}>
                    <Card key={currentTestimonial} variant="outlined" className={styles.testimonialCard}>
                        <CardContent>
                            <Typography fontSize={20}>"{testimonials[currentTestimonial].quote}"</Typography>
                            <Box className={styles.testimonialAuthor}>
                                <Avatar className={styles.testimonialAvatar}>{testimonials[currentTestimonial].name[1]}</Avatar>
                                <Typography>{testimonials[currentTestimonial].name}</Typography>
                                <Rating defaultValue={testimonials[currentTestimonial].rating} />
                            </Box>
                        </CardContent>
                    </Card>
                </animated.div>

                <Stack direction="row" sx={{ marginBottom: "20px" }}>
                    <IconButton className={styles.arrowButtons} onClick={() => handlePrevClick()}>
                        <KeyboardArrowLeftIcon sx={{ height: 50, width: 50 }} />
                    </IconButton>
                    <IconButton className={styles.arrowButtons} onClick={() => handleNextClick()}>
                        <KeyboardArrowRightIcon sx={{ height: 50, width: 50 }} />
                    </IconButton>
                </Stack>
            </Box>
        </Box>
    );
}