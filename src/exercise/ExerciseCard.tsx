import { Card, CardContent, CardCover, IconButton, Typography } from "@mui/joy";
import Favorite from '@mui/icons-material/Favorite';
import Timeline from '@mui/icons-material/Timeline';
import { Exercise } from "../types";
import { useNavigate } from "react-router-dom";

interface ExerciseCardProps {
    exercise: Exercise
}

export default function ExerciseCard(props: ExerciseCardProps) {
    const navigate = useNavigate();

    return (
        <Card variant="outlined" sx={{ height: 200, width: 280 }}>
            <CardCover>
                <img
                    src={props.exercise.imageUrl}
                    loading="lazy"
                    alt=""
                />
            </CardCover>
            <CardCover
                sx={{
                    background:
                        'linear-gradient(to top, rgba(0,0,0,0.4), rgba(0,0,0,0) 200px), linear-gradient(to top, rgba(0,0,0,0.8), rgba(0,0,0,0) 300px)',
                }}
            />
            <CardContent orientation="vertical" sx={{ justifyContent: "flex-end" }}>
                <Typography level="title-md" sx={{ color: 'white' }}>
                    {props.exercise.name}
                </Typography>
                <Typography level="body-sm" sx={{ color: "white" }}>
                    {props.exercise.description.slice(0, 60) + "..."}
                </Typography>

                <IconButton
                    aria-label="Favorite exercise"
                    size="sm"
                    variant="solid"
                    color="danger"
                    sx={{
                        position: 'absolute',
                        borderRadius: '50%',
                        right: 2,
                        bottom: 5,
                    }}
                >
                    <Favorite />
                </IconButton>

                <IconButton
                    aria-label="Exercise history"
                    size="sm"
                    variant="solid"
                    color="primary"
                    sx={{
                        position: 'absolute',
                        borderRadius: "50%",
                        left: 2,
                        bottom: 5,
                    }}
                    onClick={() => navigate(`/exercises/${props.exercise.id}`)}
                >
                    <Timeline />
                </IconButton>
            </CardContent>
        </Card>
    );
}