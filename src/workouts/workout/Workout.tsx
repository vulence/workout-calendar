import Container from '@mui/material/Container';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { DataGrid, GridActionsCellItem, GridRenderCellParams, GridRowParams } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import Fab from '@mui/material/Fab';
import styles from './workout.module.css';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import WorkoutToolbar from './WorkoutToolbar';
import AddExerciseDoneModal from './AddExerciseDoneModal';
import EditNotesModal from './EditNotesModal';

import { Workout as WorkoutType, WorkoutDataGridRows, Exercise } from '../../types';
import { fetchWorkoutById, fetchWorkoutExercises } from '../../api/api';
import { Checkbox } from '@mui/material';

export default function Workout() {
    const { id } = useParams();

    // Dialog control state
    const [openExerciseDialog, setOpenExerciseDialog] = useState(false);
    const [openNotesDialog, setOpenNotesDialog] = useState(false);

    // Data states
    const [workout, setWorkout] = useState<WorkoutType>();
    const [name, setName] = useState<string>('');
    const [weight, setWeight] = useState<number | null>();
    const [sets, setSets] = useState<number | null>();
    const [reps, setReps] = useState<number | null>();
    const [rowId, setRowId] = useState<number | null>(null);

    // Set up data grid columns
    const columns = [
        { field: 'exercise', headerName: 'Exercise', width: 200 },
        {
            field: 'weight',
            headerName: 'Weight',
            type: 'number',
            width: 120,
            renderCell: (params: GridRenderCellParams) => {
                const weightValue = params.value;
                const displayValue = weightValue === 0 ? 'Bodyweight' : weightValue;

                return <div>{displayValue}</div>
            }
        },
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
        { field: "checkbox", width: 400, type: 'actions', getActions: (params: GridRowParams) => [
                <GridActionsCellItem
                    icon={<Checkbox sx={{marginLeft: "auto"}}/>}
                    label=""
                    color="inherit"
                />,
            ]
        }
    ];

    // Initialize the workout
    useEffect(() => {
        fetchWorkoutById(id!).then(data => setWorkout(data));
    }, [setWorkout])

    // Loads in full exercises for each exercisedone of the workout
    useEffect(() => {{
        if (!workout) return;

        fetchWorkoutExercises(workout.id.toString()).then((exercises) => {
            setWorkout({
                ...workout,
                exercisesDone: workout.exercisesDone.map((exerciseDone) => ({
                    ...exerciseDone,
                    exercise: exercises.find((exercise : Exercise) => exercise.id === exerciseDone.exercise.id),
                })),
            });
        });
    }}, [workout])

    // Load data grid rows
    const loadRows = () => {
        const rows: Array<WorkoutDataGridRows> = [];

        workout?.exercisesDone?.forEach((exerciseDone) => {
            rows.push({
                id: exerciseDone.id,
                exercise: exerciseDone.exercise.name,
                weight: exerciseDone.weight,
                sets: exerciseDone.sets,
                reps: exerciseDone.reps
            })
        })

        return rows;
    };

    // Exercise done form submission
    const handleExerciseSubmit = async (exerciseId: number, weight: number, sets: number, reps: number) => {
        // If user is submitting a new done exercise, create a new object includes exercise id
        if (rowId === null) {
            const exerciseDoneDto = { exerciseId, weight, sets, reps };

            fetch(`http://localhost:8080/workouts/${id}/exercises/new`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(exerciseDoneDto),
                credentials: "include"
            }).then(() => {
                console.log("New exercise in the workout added.");
            })
        }

        // If user is editing an existing done exercise, include the id of the exercisedone to be modified
        else {
            const exerciseDone = { id: rowId, weight, sets, reps };

            fetch(`http://localhost:8080/workouts/${id}/exercises/update`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(exerciseDone),
                credentials: "include"
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
            credentials: "include",
            body: JSON.stringify(params.id)
        }).then(() => {
            console.log("Exercise done in workout deleted.");
            window.location.reload();
        }).catch((error) => {
            console.error(error);
        });
    };

    // Set parameters to the corresponding row that's being edited
    const handleEditClick = (params: GridRowParams) => () => {
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
            credentials: "include",
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
            credentials: "include",
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