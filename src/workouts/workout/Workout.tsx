import { Container, Fab, Checkbox, Skeleton } from '@mui/material';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { DataGrid, GridActionsCellItem, GridRenderCellParams, GridRowParams } from '@mui/x-data-grid';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import styles from './workout.module.css';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';
import WorkoutToolbar from './WorkoutToolbar';
import AddWorkoutExerciseModal from './AddWorkoutExerciseModal';
import EditNotesModal from './EditNotesModal';
import { darken, styled } from '@mui/material/styles';

import { Workout as WorkoutType, WorkoutDataGridRows, WorkoutExercise } from '../../types';
import { deleteWorkoutExercise, fetchWorkoutById, fetchWorkoutExercises, updateWorkout, submitWorkoutExercise, updateWorkoutExercise } from '../../api/api';
import LoadingModal from '../../common/LoadingModal';

export default function Workout() {
    const { id } = useParams();

    // Dialog control state
    const [openExerciseDialog, setOpenExerciseDialog] = useState(false);
    const [openNotesDialog, setOpenNotesDialog] = useState(false);

    // Data states
    const [workout, setWorkout] = useState<WorkoutType>();
    const [workoutExercises, setWorkoutExercises] = useState<WorkoutExercise[]>();
    const [name, setName] = useState<string>('');
    const [weight, setWeight] = useState<number | null>();
    const [sets, setSets] = useState<number | null>();
    const [reps, setReps] = useState<number | null>();
    const [rowId, setRowId] = useState<number | null>(null);

    // Displays loading modal if a workout exercise is submittng/deleting
    const [loading, setLoading] = useState<boolean>(false);

    // Initialize the workout and map all corresponding exercises to workoutExercise objects
    useEffect(() => {
        Promise.all([fetchWorkoutById(id!), fetchWorkoutExercises(id!.toString())]).then(
            ([workout, workoutExercises]) => {
                setWorkout(workout);
                setWorkoutExercises(workoutExercises);
            }
        );
    }, []);

    // Adds colored rows for completed exercises
    const StyledDataGrid = styled(DataGrid)(({ theme }) => ({
        '& .super-app-theme--Completed': {
            backgroundColor: darken(theme.palette.success.main, 0.7),
            '&:hover': {
                backgroundColor: darken(theme.palette.success.main, 0.6),
            },
        },
    }));

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
        {
            field: 'completed', headerName: 'Completed', width: 120, renderCell: (params: GridRenderCellParams) => {
                return (
                    <Checkbox checked={params.row.completed} sx={{alignSelf: "center", justifySelf: "center"}} onChange={() => handleCompleted(params)} />
                );
            }
        }
    ];

    // Update the workoutExercise that was marked as (not)completed
    const handleCompleted = (params: GridRenderCellParams) => {
        const workoutExercise = workoutExercises!.find(we => we.id === params.id)!; // Guaranteed to exist since the row id is equal to the workoutexercise id
        workoutExercise.completed = !workoutExercise.completed;

        setWorkoutExercises(prevState => prevState!.map(we => (we.id === params.id ? workoutExercise : we)));
        updateWorkoutExercise(workout!.id.toString(), workoutExercise).then(data => console.log(data));
    }

    // Load data grid rows
    const loadRows = () => {
        const rows: Array<WorkoutDataGridRows> = [];

        workoutExercises?.forEach((workoutExercise) => {
            rows.push({
                id: workoutExercise.id,
                exercise: workoutExercise.exerciseName,
                weight: workoutExercise.weight,
                sets: workoutExercise.sets,
                reps: workoutExercise.reps,
                completed: workoutExercise.completed
            })
        })

        return rows;
    };

    // Exercise done form submission
    const handleExerciseSubmit = async (exerciseId: number, weight: number, sets: number, reps: number) => {
        // If user is submitting a new done exercise, create a new object includes exercise id
        if (rowId === null) {
            const workoutExerciseDto = { exerciseId, weight, sets, reps };
            
            setLoading(true);
            submitWorkoutExercise(id!.toString(), workoutExerciseDto).then(() => {setLoading(false); window.location.reload()}).catch(error => console.error(error));
        }

        // If user is editing an existing done exercise, include the id of the workoutExercise to be modified
        else {
            const workoutExercise = { id: rowId, weight, sets, reps };

            setLoading(true);
            updateWorkoutExercise(id!.toString(), workoutExercise).then(() => {setLoading(false); window.location.reload()}).catch(error => console.error(error));
        }
    };

    // Delete an exercise from a workout
    const handleDeleteClick = (params: GridRowParams) => () => {
        setLoading(true);
        deleteWorkoutExercise(id!.toString(), params.id.toString()).then(() => {setLoading(false); window.location.reload();}).catch(error => console.error(error));
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
        const updatedWorkout = { ...workout!, notes: notes };
        setWorkout(updatedWorkout);

        updateWorkout(id!.toString(), updatedWorkout).then(() => setOpenNotesDialog(false));
    };

    // Duration submission
    const handleDurationSubmit = (minutes: number) => {
        const updatedWorkout = { ...workout!, duration: minutes };
        setWorkout(updatedWorkout);

        updateWorkout(id!.toString(), updatedWorkout).then(() => window.location.reload());
    };

    return (
        <Container>
            {!workoutExercises ? (
                <>
                <Skeleton animation="wave" variant="rectangular" width="50%" height={20} />
                <Skeleton animation="wave" variant="rectangular" width="20%" sx={{marginTop: "10px"}} />
                <Skeleton animation="wave" variant="rectangular" width="20%" sx={{marginTop: "10px"}} />
                </>
            ) : (
                <>
                <StyledDataGrid
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
                    getRowClassName={(params) => params.row.completed ? 'super-app-theme--Completed' : 'super-app-theme--NotCompleted'}
                    disableRowSelectionOnClick
                    hideFooter
                />
    
                <Fab className={styles.fab} color="primary" variant="extended" aria-label="add" onClick={handleOpenExerciseDialog}>
                    <FitnessCenterIcon sx={{ mr: 1 }} />
                    Add Exercise
                </Fab>
                </>
            )}

            <AddWorkoutExerciseModal
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

            <LoadingModal isLoading={loading} />
        </Container>
    )
}