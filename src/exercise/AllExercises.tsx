import React from 'react';
import { useEffect, useState } from 'react';
import { Container, IconButton, Collapse, Typography, Grid, Card, CardActions, CardContent, CardHeader, CardMedia } from '@mui/material';
import TimelineIcon from '@mui/icons-material/Timeline';
import { Link } from "react-router-dom";
import { Helmet } from 'react-helmet';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { styled } from '@mui/material/styles';

import { ExerciseDto, ExpandMoreProps, ExpandedState, AllExercisesDataGridRows } from '../types';
import ExerciseFilter from './ExerciseFilter';

export default function AllExercises() {
    // Data states
    const [exercises, setExercises] = useState<Array<ExerciseDto>>([]);
    const [muscleGroupFilter, setMuscleGroupFilter] = useState<string>('');

    // Card expansion state
    const [expanded, setExpanded] = useState<ExpandedState>({});

    // Initialize all exercises
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/exercises");
            const result = await response.json();
            setExercises(result);
        };
        fetchData();
    }, [setExercises]);

    // Used for collapsable cards
    const ExpandMore = styled((props: ExpandMoreProps & React.ComponentProps<typeof IconButton>) => {
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
    const getRows = (exercises: Array<ExerciseDto>) => {
        const rows: Array<AllExercisesDataGridRows> = [];

        exercises.forEach((exercise) => {
            let muscles: string = "";

            exercise.muscleGroups.forEach((muscleGroup) => muscles += muscleGroup.name + ", ");
            muscles = muscles.slice(0, -2);

            rows.push({
                id: exercise.id,
                name: exercise.name,
                description: exercise.description,
                muscleGroups: muscles
            })
        });

        return rows;
    }

    // Filters rows and calls the getRows function to structure them
    const filteredRows = (exercises: Array<ExerciseDto>) => {
        // If there are no filters, return all exercises
        if (muscleGroupFilter === '') {
            return getRows(exercises);
        }

        // If there are filters, apply them
        return getRows(exercises.filter(exercise =>
            exercise.muscleGroups.some(muscleGroup =>
                muscleGroup.name === muscleGroupFilter
            )
        ));
    }

    // Callback function for exerciseFilter
    const handleFilters = (muscleGroupName: string) => {
        setMuscleGroupFilter(muscleGroupName);
    }

    // Expand card handler
    const handleExpandClick = (id: number) => {
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

            <ExerciseFilter 
                updateParentValues={handleFilters} 
            />

            <Grid container spacing={2} sx={{ marginBottom: "30px", marginTop: "15px" }}>
                {filteredRows(exercises).sort((a, b) => a.name > b.name ? 1 : -1).map(ex => (
                    <Grid key={ex.id} item xs={1} sm={2} md={3} sx={{ minWidth: "200px" }}>
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
                                <IconButton component={Link} to={exercises.find(e => e.name === ex.name)?.id.toString() + "/history"}>
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
        </Container>
    );
}