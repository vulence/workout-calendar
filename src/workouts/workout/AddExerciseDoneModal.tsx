import * as React from 'react';
import { useState, useEffect } from 'react';
import Dialog from '@mui/material/Dialog';
import Box from '@mui/material/Box';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Autocomplete from '@mui/material/Autocomplete';
import styles from './workout.module.css';
import Switch from '@mui/material/Switch';

import { AddExerciseDoneModalProps, ExerciseDto } from '../../types';
import Typography from '@mui/material/Typography';

export default function AddExerciseDoneModal(props: AddExerciseDoneModalProps) {
    // Getting all exercises from the prop
    const exercises: Array<ExerciseDto> = props.exercises;

    // Data states
    const [name, setName] = useState<string>(props.name);
    const [weight, setWeight] = useState<number>(props.weight | 0);
    const [sets, setSets] = useState<number>(props.sets | 0);
    const [reps, setReps] = useState<number>(props.reps | 0);

    // Form validation states
    const [weightValid, setWeightValid] = useState<boolean>(true);
    const [setsValid, setSetsValid] = useState<boolean>(true);
    const [repsValid, setRepsValid] = useState<boolean>(true);

    // Controls whether bodyweight is selected
    const [isSwitchOn, setSwitchOn] = useState<boolean>(false);

    const handleSwitchChange = () => {
        setSwitchOn(!isSwitchOn);
    };

    // Synchronizes the fields if the user wants to edit an existing exercise
    useEffect(() => {
        setName(props.name);
    }, [props.name])

    useEffect(() => {
        if (props.weight === 0) {
            setSwitchOn(true);
        }
        else {
            setWeight(props.weight);
            setSwitchOn(false);
        }
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

        if (exerciseId == null || (!weightValid && !isSwitchOn) || !setsValid || !repsValid) {
            return;
        }

        props.handleSubmit(exerciseId, isSwitchOn ? 0 : weight, sets, reps);
    };

    return (
        <Dialog open={props.open} onClose={props.handleClose}>
            <DialogTitle margin="10px">Add exercise</DialogTitle>
            <DialogContent>
                <Autocomplete
                    id="exercise-name"
                    fullWidth
                    value={exercises ? exercises.find(ex => ex.name === props.name) : null}
                    options={exercises}
                    getOptionLabel={(exercises) => exercises.name}
                    groupBy={(exercises) => exercises.muscleGroups[0].name}
                    className={styles.autoComplete}
                    onChange={(e, newValue) => setName(newValue?.name || '')}
                    renderInput={(params) => <TextField {...params} label="Exercise name" />}
                    disabled={name ? true : false}
                />

                <Box display="flex" alignItems="center" justifyContent="center" marginTop={2}>
                    <TextField
                        fullWidth
                        required
                        disabled={isSwitchOn}
                        error={!weightValid && !isSwitchOn}
                        helperText={!weightValid && !isSwitchOn ? "You must enter a positive integer" : ""}
                        className={styles.textField}
                        id="weight"
                        label={!isSwitchOn ? "Weight (kg)" : "Selected body weight"}
                        value={weight}
                        onChange={(e) => {
                            const inputValue: string = e.target.value;
                            const parsedWeight: number = parseFloat(inputValue);

                            setWeight(!isNaN(parsedWeight) ? parsedWeight : 0);
                            setWeightValid(!isNaN(parsedWeight) && parsedWeight > 0);
                        }}
                    />
                    <Box display="flex" flexDirection="column" alignItems="center" justifyContent="center" marginLeft={3}>
                        <Typography noWrap fontSize={12}>Body weight</Typography>
                        <Switch checked={isSwitchOn} onChange={handleSwitchChange} />
                    </Box>
                </Box>
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
                        const inputValue: string = e.target.value;
                        const parsedSets: number = parseFloat(inputValue);

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
                        const inputValue: string = e.target.value;
                        const parsedReps: number = parseFloat(inputValue);

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