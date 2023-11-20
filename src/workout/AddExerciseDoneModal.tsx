import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Autocomplete from '@mui/material/Autocomplete';
import styles from './workout.module.css';
import { useState, useEffect } from 'react';

import { AddExerciseDoneModalProps, ExerciseDto } from '../types';

export default function AddExerciseDoneModal(props : AddExerciseDoneModalProps) {
    // Getting all exercises from the prop
    const exercises : Array<ExerciseDto> = props.exercises;

    // Data states
    const [name, setName] = useState<string>(props.name);
    const [weight, setWeight] = useState<number>(props.weight);
    const [sets, setSets] = useState<number>(props.sets);
    const [reps, setReps] = useState<number>(props.reps);

    // Form validation states
    const [weightValid, setWeightValid] = useState<boolean>(false);
    const [setsValid, setSetsValid] = useState<boolean>(false);
    const [repsValid, setRepsValid] = useState<boolean>(false);

    // Synchronizes the fields if the user wants to edit an existing exercise
    useEffect(() => {
        setName(props.name);
    }, [props.name])

    useEffect(() => {
        setWeight(props.weight);
    }, [props.weight])

    useEffect(() => {
        setSets(props.sets);
    }, [props.sets])

    useEffect(() => {
        setReps(props.reps);
    }, [props.reps])

    // Validate data before calling parent submit
    const handleSubmit = () => {
        const exerciseId = exercises.find(ex => ex.name === name)?.id;

        if (exerciseId == null || !weightValid || !setsValid || !repsValid) {
            return;
        }

        props.handleSubmit(exerciseId, weight, sets, reps);
    };

    return (
        <Dialog open={props.open} onClose={props.handleClose}>
            <DialogTitle margin="10px">Add exercise</DialogTitle>
            <DialogContent>
                <Autocomplete
                    id="exercise-name"
                    value={exercises?.find(ex => ex.name === props.name)}
                    options={exercises}
                    getOptionLabel={(exercises) => exercises.name}
                    groupBy={(exercises) => exercises.muscleGroups[0].name}
                    className={styles.autoComplete}
                    onChange={(e, newValue) => setName(newValue?.name || '')}
                    renderInput={(params) => <TextField {...params} label="Exercise name" />}
                />
                <TextField
                    fullWidth
                    required
                    error={!weightValid}
                    helperText={!weightValid ? "You must enter a positive integer" : ""}
                    className={styles.textField}
                    id="weight"
                    label="Weight (kg)"
                    value={weight}
                    onChange={(e) => {
                        const inputValue : string = e.target.value;
                        const parsedWeight : number = parseFloat(inputValue);

                        setWeight(!isNaN(parsedWeight) ? parsedWeight : 0);
                        setWeightValid(!isNaN(parsedWeight) && parsedWeight > 0);
                    }}
                />
                <TextField
                    fullWidth
                    required
                    error={!setsValid}
                    helperText={!setsValid ? "You must enter a positive integer" : ""}
                    className={styles.textField}
                    id="sets"
                    label="Sets"
                    value={sets}
                    onChange={(e) => {
                        const inputValue : string = e.target.value;
                        const parsedSets : number = parseFloat(inputValue);

                        setSets(!isNaN(parsedSets) ? parsedSets : 0);
                        setSetsValid(!isNaN(parsedSets) && parsedSets > 0 && Number.isInteger(parsedSets));
                    }}
                />
                <TextField
                    fullWidth
                    required
                    error={!repsValid}
                    helperText={!repsValid ? "You must enter a positive integer" : ""}
                    className={styles.textField}
                    id="reps"
                    label="Reps"
                    value={reps}
                    onChange={(e) => {
                        const inputValue : string = e.target.value;
                        const parsedReps : number = parseFloat(inputValue);

                        setReps(!isNaN(parsedReps) ? parsedReps : 0);
                        setRepsValid(!isNaN(parsedReps) && parsedReps > 0 && Number.isInteger(parsedReps));
                    }}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={props.handleClose} variant="contained" color="error">Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="success">Submit</Button>
            </DialogActions>
        </Dialog>
    )
}