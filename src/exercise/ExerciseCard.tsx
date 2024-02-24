import { Card, CardContent, CardCover, CardOverflow, Divider, IconButton, Typography } from "@mui/joy";
import Favorite from '@mui/icons-material/Favorite';

export default function ExerciseCard() {

    return (
        <Card variant="outlined" sx={{ height: 220, width: 280 }}>
            <CardCover>
                <img
                    src="https://caliberstrong.com/wp-content/uploads/2020/04/how-to-bench-press.jpg"
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
            <CardContent sx={{ justifyContent: "flex-end" }}>
                <Typography level="title-md" sx={{ color: 'white' }}>
                    Bench press
                </Typography>
                <Typography level="body-sm" sx={{ color: "white" }}>
                    Chest
                </Typography>

                <CardOverflow>
                    <CardContent orientation="horizontal">
                        <Typography level="body-xs"></Typography>
                        <IconButton
                            aria-label="Like minimal photography"
                            size="md"
                            variant="solid"
                            color="danger"
                            sx={{
                                position: 'absolute',
                                borderRadius: '50%',
                                right: '0.5rem',
                                bottom: 26,
                                transform: 'translateY(50%)',
                            }}
                        >
                            <Favorite />
                        </IconButton>
                    </CardContent>
                </CardOverflow>
            </CardContent>
        </Card>
    );
}