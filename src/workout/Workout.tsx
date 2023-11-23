import * as React from 'react';
import Container from '@mui/material/Container';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { DataGrid, GridActionsCellItem, GridRowParams } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import Fab from '@mui/material/Fab';
import styles from './workout.module.css';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import WorkoutToolbar from './WorkoutToolbar';
import AddExerciseDoneModal from './AddExerciseDoneModal';
import EditNotesModal from './EditNotesModal';

import { Workout, ExerciseDto, WorkoutDataGridRows } from '../types';

export default function AWorkout() {
    const { id } = useParams();

    // Dialog control state
    const [openExerciseDialog, setOpenExerciseDialog] = useState(false);
    const [openNotesDialog, setOpenNotesDialog] = useState(false);

    // Data states
    const [workout, setWorkout] = useState<Workout>();
    const [exercise, setExercise] = useState<Array<ExerciseDto>>();
    const [name, setName] = useState<string>('');
    const [weight, setWeight] = useState<number | null>();
    const [sets, setSets] = useState<number | null>();
    const [reps, setReps] = useState<number | null>();
    const [rowId, setRowId] = useState<number | null>(null);

    // Set up data grid columns
    const columns = [
        { field: 'exercise', headerName: 'Exercise', width: 200 },
        { field: 'weight', headerName: 'Weight', type: 'number', width: 100 },
        { field: 'sets', headerName: 'Sets', type: 'number', width: 100 },
        { field: 'reps', headerName: 'Reps', type: 'number', width: 100 },
        {
            field: 'actions', headerName: 'Actions', type: 'actions', width: 180, cellClassName: 'actions',
            getActions: (params: GridRowParams) => {
                return [
                    <GridActionsCellItem
                        icon={<DeleteIcon />}
                        label="Delete"
                        onClick={handleDeleteClick(params)}
                        color="inherit"
                    />,
                    <GridActionsCellItem
                        icon={<EditIcon />}
                        label="Edit"
                        onClick={handleEditClick(params)}
                        color="inherit"
                    />,
                ];
            }
        },
    ];

    // Initialize the workout
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/workouts/" + id, {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem('token')}`,
                },
            });
            const result = await response.json();
            setWorkout(result);
        };
        fetchData();
    }, [setWorkout])

    // Initialize exercises
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/exercises")
            const result = await response.json();
            setExercise(result);
        };
        fetchData();
    }, [setExercise])

    // Load data grid rows
    const loadRows = () => {
        const rows: Array<WorkoutDataGridRows> = [];

        workout?.exercisesDone?.forEach((exerciseDone) => {
            const exerciseName = exercise?.find(ex => ex.id === exerciseDone.exercise.id)?.name;

            if (exerciseName !== undefined) rows.push({
                id: exerciseDone.id,
                exercise: exerciseName,
                weight: exerciseDone.weight,
                sets: exerciseDone.sets,
                reps: exerciseDone.reps
            })
        })

        return rows;
    };

    // Exercise done form submission
    const handleExerciseSubmit = (exerciseId: number, weight: number, sets: number, reps: number) => {
        if (rowId === null) {
            const exerciseDoneDto = { exerciseId, weight, sets, reps };

            fetch(`http://localhost:8080/workouts/${id}/exercises/new`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem('token')}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(exerciseDoneDto)
            }).then(() => {
                console.log("New exercise in the workout added.");
            })
        }

        else {
            const dataGridExerciseDto = { rowId, weight, sets, reps };
            fetch(`http://localhost:8080/workouts/${id}/exercises/update`, {
                method: "PUT",
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem('token')}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(dataGridExerciseDto)
            }).then(() => {
                console.log("Exercise successfully updated");
            })
        }

        window.location.reload();
    };

    // Delete an exercise from a workout
    const handleDeleteClick = (params: GridRowParams) => () => {
        fetch(`http://localhost:8080/workouts/${id}/exercises`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(params.id)
        }).then(() => {
            console.log("Exercise done in workout deleted.");
            window.location.reload();
        }).catch((error) => {
            console.error(error);
        });
    };

    // Set parameters to the corresponding row that's being edited
    const handleEditClick = (params : GridRowParams) => () => {
        setName(params.row.exercise);
        setWeight(params.row.weight);
        setSets(params.row.sets);
        setReps(params.row.reps);
        setRowId(params.row.id);

        handleOpenExerciseDialog();
    };

    // Notes dialog handlers
    const handleOpenNotesDialog = () => {
        setOpenNotesDialog(true);
    };

    const handleCloseNotesDialog = () => {
        setOpenNotesDialog(false);
    };

    // Exercise dialog handlers
    const handleOpenExerciseDialog = () => {
        setOpenExerciseDialog(true);
    };

    const handleCloseExerciseDialog = () => {
        setOpenExerciseDialog(false);

        setName('');
        setWeight(null);
        setSets(null);
        setReps(null);
        setRowId(null);
    };

    // Notes form submission
    const handleNotesSubmit = (notes: string) => {
        fetch(`http://localhost:8080/workouts/${id}/setNotes`, {
            method: "PUT",
            body: notes
        }).then(() => {
            console.log("Workout notes successfully modified.");
        })

        setOpenNotesDialog(false);
    };

    // Duration submission
    const handleDurationSubmit = (minutes: number) => {
        fetch(`http://localhost:8080/workouts/${id}/setDuration`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(minutes)
        }).then(() => {
            console.log("Workout duration successfully modified.");
        })

        window.location.reload();
    };

    return (
        <Container>
            <DataGrid
                rows={loadRows()}
                columns={columns}
                slots={{
                    toolbar: WorkoutToolbar,
                }}
                slotProps={{
                    toolbar: { handleOpenNotesDialog, handleDurationSubmit, workout }
                }}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 15 },
                    },
                }}
                className={styles.dataGrid}
                pageSizeOptions={[10, 15]}
                disableRowSelectionOnClick
                hideFooter
            />

            <Fab className={styles.fab} color="primary" variant="extended" aria-label="add" onClick={handleOpenExerciseDialog}>
                <FitnessCenterIcon sx={{ mr: 1 }} />
                Add Exercise
            </Fab>

            <AddExerciseDoneModal
                open={openExerciseDialog}
                handleClose={handleCloseExerciseDialog}
                handleSubmit={handleExerciseSubmit}
                exercises={exercise!}
                name={name}
                weight={weight!}
                sets={sets!}
                reps={reps!}
            />

            <EditNotesModal
                open={openNotesDialog}
                handleClose={handleCloseNotesDialog}
                handleSubmit={handleNotesSubmit}
                notes={workout ? workout.notes : "nema"}
            />

        </Container>
    )
}