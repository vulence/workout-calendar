import React from 'react';
import { useEffect, useState } from 'react';
import TextField from '@mui/material/TextField';
import Container from '@mui/material/Container';
import Button from '@mui/material/Button';
import TimelineIcon from '@mui/icons-material/Timeline';
import IconButton from '@mui/material/IconButton';
import { Link } from "react-router-dom";
import { Helmet } from 'react-helmet';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Collapse from '@mui/material/Collapse';
import Typography from '@mui/material/Typography';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Grid from '@mui/material/Grid';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';

import { ExerciseDto, ExpandMoreProps, ExpandedState, AllExercisesDataGridRows } from '../types';

export default function AllExercises() {
    // Data states
    const [name, setName] = useState<String>('');
    const [exercise, setExercise] = useState<Array<ExerciseDto>>([]);

    // Dialog control state
    const [open, setOpen] = useState<boolean>(false);

    // Card expansion state
    const [expanded, setExpanded] = useState<ExpandedState>({});

    // Used for collapsable cards
    const ExpandMore = styled((props : ExpandMoreProps & React.ComponentProps<typeof IconButton>) => {
        const { expand, ...other } = props;
        return <IconButton {...other} />;
    })(({ theme, expand }) => ({
        transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
        marginLeft: 'auto',
        transition: theme.transitions.create('transform', {
            duration: theme.transitions.duration.shortest,
        }),
    }));

    // Load data grid rows
    const getRows = (exercise : Array<ExerciseDto>) => {
        const rows : Array<AllExercisesDataGridRows> = [];

        exercise.forEach((exercise) => {
            let muscles : string = "";

            exercise.muscleGroups.forEach((muscleGroup) => muscles += muscleGroup.name + ", ");
            muscles = muscles.slice(0, -2);

            rows.push({
                id: exercise.id,
                name: exercise.name,
                description: exercise.description,
                muscleGroups: muscles
            })
        })

        return rows;
    }

    // Initialize all exercises
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/exercises");
            const result = await response.json();
            setExercise(result);
        };
        fetchData();
    }, [setExercise])

    // Exercise form submission
    const handleSubmit = (e : React.MouseEvent<HTMLButtonElement>) => {
        e.preventDefault();

        const exercise = { name };
        fetch("http://localhost:8080/exercises/new", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(exercise)
        }).then(() => {
            console.log("New exercise added");
        })

        window.location.reload();
    }

    // Dialog open handler
    const handleClickOpen = () => {
        setOpen(true);
    };

    // Dialog close handler
    const handleClose = () => {
        setOpen(false);
    };

    // Expand card handler
    const handleExpandClick = (id : number) => {
        setExpanded(expanded => ({
            ...expanded,
            [id]: !expanded[id],
        }));
    };

    return (
        <Container>
            <Helmet>
                <title>All Exercises</title>
            </Helmet>

            <Grid container spacing={2} sx={{ marginBottom: "30px", marginTop: "15px" }}>
                {getRows(exercise).map(ex => (
                    <Grid item xs={1} sm={2} md={3} sx={{ minWidth: "200px" }}>
                        <Card key={ex.id.toString()} variant="outlined">
                            <CardMedia
                                height="200"
                                sx={{ background: 'white' }}
                                component="img"
                                image={ex.name.replace(/\s+/g, '').toLowerCase() + '.png'}
                            />
                            <CardHeader
                                title={ex.name}
                                subheader={ex.muscleGroups}
                            />
                            <CardActions>
                                <IconButton component={Link} to={exercise.find(e => e.name === ex.name)?.id.toString() + "/history"}>
                                    <TimelineIcon />
                                </IconButton>
                                <ExpandMore
                                    expand={expanded[ex.id]}
                                    onClick={() => handleExpandClick(ex.id)}
                                    aria-expanded={expanded[ex.id]}
                                    aria-label="show more"
                                >
                                    <ExpandMoreIcon />
                                </ExpandMore>
                            </CardActions>
                            <Collapse in={expanded[ex.id]} timeout="auto" unmountOnExit>
                                <CardContent>
                                    <Typography paragraph>
                                        {ex.description}
                                    </Typography>
                                </CardContent>
                            </Collapse>
                        </Card>
                    </Grid>
                ))}
            </Grid>

            <Button sx={{ fontSize: "20px" }} variant="contained" startIcon={<FitnessCenterIcon />} size="large" onClick={handleClickOpen}>
                ADD EXERCISE
            </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle margin="10px">Add exercise</DialogTitle>
                <DialogContent>
                    <TextField required
                        fullWidth
                        sx={{
                            margin: '10px 0 0 0'
                        }}
                        id="exercise-name"
                        label="Exercise name"
                        variant="outlined"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} variant="outlined" color="error">Cancel</Button>
                    <Button onClick={(e) => handleSubmit(e)} variant="contained" color="success">Submit</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}