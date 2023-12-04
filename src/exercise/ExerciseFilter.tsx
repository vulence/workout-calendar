import { useState, useEffect } from 'react';
import { Container, Typography, Select, MenuItem } from '@mui/material';

import { ExerciseFilterProps, MuscleGroup } from '../types';
import { fetchMuscleGroups } from '../api/api';

export default function ExerciseFilter(props : ExerciseFilterProps) {
    // Data states
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);

    // Loads all the muscle groups
    useEffect(() => {
        fetchMuscleGroups().then(data => setMuscleGroups(data));
    }, [muscleGroups]);

    // Handles the value changes
    const handleChange = (newMuscleGroup : string) => {
        props.updateParentValues(newMuscleGroup);
    }

    return (
        <Container sx={{ display: 'flex', flexDirection: 'row', marginTop: 2, alignItems: "center" }}>
            <Typography color="grey" fontWeight="bold">Muscle group:</Typography>
            <Select
                id="muscle-groups"
                sx={{
                    width: "200px",
                    marginLeft: 2
                }}
                onChange={(e : any) => handleChange(e.target.value)}
            >
                <MenuItem value=""><em>All</em></MenuItem>
                {muscleGroups.map(muscleGroup =>
                    <MenuItem key={muscleGroup.id} value={muscleGroup.name}>{muscleGroup.name}</MenuItem>
                )}
            </Select>
        </Container>
    );
}