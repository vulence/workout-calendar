import React from 'react';
import { useEffect, useState } from 'react';
import { Container, IconButton, Collapse, Typography, Grid, Card, CardActions, CardContent, CardHeader, CardMedia, Button } from '@mui/material';
import TimelineIcon from '@mui/icons-material/Timeline';
import { Link } from "react-router-dom";
import { Helmet } from 'react-helmet';
import { Watch } from 'react-loader-spinner';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { styled } from '@mui/material/styles';
import { AccordionGroup } from '@mui/joy';

import { ExerciseDto, ExpandMoreProps, ExpandedState, AllExercisesDataGridRows, MuscleGroup } from '../types';
import { fetchExercises, fetchMuscleGroups } from '../api/api';
import MuscleGroupAccordion from './MuscleGroupAccordion';

export default function AllExercises() {
    // Data states
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        setLoading(true);
        fetchMuscleGroups().then((data) => { setMuscleGroups(data); setLoading(false) });
    }, []);

    return (
        <Container>
            <Helmet>
                <title>All Exercises</title>
            </Helmet>

            <Watch
                visible={loading}
                height="80"
                width="80"
                radius="48"
                color="white"
                ariaLabel="watch-loading"
                wrapperStyle={{
                    justifyContent: "center",
                    marginTop: "50px"
                }}
                wrapperClass=""
            />

            <AccordionGroup
                transition="0.2s"
                sx={{ marginTop: "10px", borderRadius: "30px" }}
            >
                {muscleGroups.sort((a, b) => a.name > b.name ? 1 : -1).map((muscleGroup) => (
                    <MuscleGroupAccordion muscleGroup={muscleGroup} />
                ))}
            </AccordionGroup>
        </Container>
    );
}