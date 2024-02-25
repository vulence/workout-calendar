import { Card, CardContent, CardCover, CardOverflow, IconButton, Typography } from "@mui/joy";
import Favorite from '@mui/icons-material/Favorite';
import { Exercise } from "../types";

interface ExerciseCardProps {
    exercise: Exercise
}

export default function ExerciseCard(props: ExerciseCardProps) {
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
                    aria-label="Like minimal photography"
                    size="md"
                    variant="solid"
                    color="danger"
                    sx={{
                        position: 'absolute',
                        borderRadius: '50%',
                        right: 0,
                        bottom: 0,
                    }}
                >
                    <Favorite />
                </IconButton>
            </CardContent>
        </Card>
    );
}