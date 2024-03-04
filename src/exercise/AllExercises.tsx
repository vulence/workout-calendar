import { useEffect, useState } from 'react';
import { Container } from '@mui/material';
import { Helmet } from 'react-helmet';
import { AccordionGroup } from '@mui/joy';

import { Exercise, MuscleGroup } from '../types';
import { fetchExercisesByMuscleGroup, fetchMuscleGroups } from '../api/api';
import MuscleGroupAccordion from './MuscleGroupAccordion';
import LoadingModal from '../common/LoadingModal';

interface MuscleGroupExercises {
    [id: number]: Exercise[];
};

export default function AllExercises() {
    // Data states
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);
    const [muscleGroupExercises, setMuscleGroupExercises] = useState<MuscleGroupExercises>({});
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        setLoading(true);
        fetchMuscleGroups().then((data) => { setMuscleGroups(data); setLoading(false) });
    }, []);

    // Loads exercises for the corresponding accordion, if they weren't previously loaded
    const handleAccordionExpand = (muscleGroupId: number) => {
        if (muscleGroupExercises[muscleGroupId] !== undefined) return;

        fetchExercisesByMuscleGroup(muscleGroupId.toString()).then((data) => {
            setMuscleGroupExercises({ ...muscleGroupExercises, [muscleGroupId]: data });
        });
    };

    return (
        <Container>
            <Helmet>
                <title>All Exercises</title>
            </Helmet>

            <LoadingModal isLoading={loading} />

            <AccordionGroup
                transition="0.2s"
                sx={{ marginTop: "10px", borderRadius: "30px" }}
            >
                {muscleGroups.sort((a, b) => a.name > b.name ? 1 : -1).map((muscleGroup) => (
                    <MuscleGroupAccordion key={muscleGroup.id} muscleGroup={muscleGroup} exercises={muscleGroupExercises[muscleGroup.id]} handleClick={handleAccordionExpand} />
                ))}
            </AccordionGroup>
        </Container>
    );
}