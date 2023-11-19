import React from 'react';
import { useEffect, useState } from 'react';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography';
import { DataGrid, GridCellParams } from '@mui/x-data-grid';
import type {} from '@mui/x-data-grid/themeAugmentation';
import { useParams } from 'react-router-dom';
import { LineChart } from '@mui/x-charts/LineChart';
import ExerciseHistoryToolbar from './ExerciseHistoryToolbar';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import TrendingDownIcon from '@mui/icons-material/TrendingDown';
import TrendingFlatIcon from '@mui/icons-material/TrendingFlat';

import { Exercise, ExerciseHistoryDto } from '../types';

export default function ExerciseHistory() {
    const { id } = useParams();

    // Data states
    const [exercise, setExercise] = useState<Exercise | null>(null);
    const [maxWeight, setMaxWeight] = useState<Array<ExerciseHistoryDto> | null>(null);
    const [exerciseHistory, setExerciseHistory] = useState<Array<ExerciseHistoryDto> | null>(null);

    const columns = [
        { field: 'date', headerName: 'Date', width: 130 },
        {
            field: 'weight',
            headerName: 'Weight',
            width: 120,
            renderCell: (params : GridCellParams) => {
                /* Finds the index of the current workout so that it can compare it with previous one
                    (data grid rows have IDs according to workouts, and the exerciseHistory has them in order, so we use workout ID to find their index)
                */
                const index : number = exerciseHistory ? exerciseHistory.findIndex((ex) => ex.id === params.row.id) : -1;
                const currentWorkout : ExerciseHistoryDto | null = exerciseHistory ? exerciseHistory[index] : null;
                const previousWorkout : ExerciseHistoryDto | null = exerciseHistory ? exerciseHistory[index - 1] : null;

                if (previousWorkout && currentWorkout && currentWorkout.weight > previousWorkout.weight) {
                    return (
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                             {currentWorkout.weight}
                            <TrendingUpIcon sx={{ marginLeft: '5px' }} color="success" />
                        </div>
                    )
                }
                else if (previousWorkout && currentWorkout && currentWorkout.weight < previousWorkout.weight) {
                    return (
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                             {currentWorkout.weight}
                            <TrendingDownIcon sx={{ marginLeft: '5px' }} color="error" />
                        </div>
                    )
                }
                else {
                    return (
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                             {currentWorkout?.weight}
                            <TrendingFlatIcon sx={{ marginLeft: '5px' }} />
                        </div>
                    )
                }
            }
        },
        { field: 'sets', headerName: 'Sets', width: 100 },
        { field: 'reps', headerName: 'Reps', width: 100 },
    ];

    // Initialize the exercise
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/exercises/${id}`);
            const result = await response.json();
            setExercise(result);
        }
        fetchData();
    }, [id])

    // Initialize max weights for exercise
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/exercises/${id}/workouts`);
            const result = await response.json();
            setMaxWeight(result);
        }
        fetchData();
    }, [id])

    // Initialize exercise history
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`http://localhost:8080/exercises/${id}/exerciseHistory`);
            const result = await response.json();
            setExerciseHistory(result);
        }
        fetchData();
    }, [id])

    return (
        <Container>
            <DataGrid
                rows={exerciseHistory!}
                columns={columns}
                slots={{
                    toolbar: ExerciseHistoryToolbar,
                }}
                slotProps={{
                    toolbar: { exercise }
                }}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 15 },
                    },
                }}
                sx={{
                    margin: "20px 0"
                }}
                pageSizeOptions={[10, 15]}
                disableRowSelectionOnClick
                hideFooter
            />


            {maxWeight!.length > 0 &&
                <Container sx={{ background: 'black', opacity: 0.9 }}>
                    <Typography variant="h1" color="text.secondary">Max Weights</Typography>
                    <LineChart
                        xAxis={[{ scaleType: 'band', dataKey: 'date' }]}
                        series={[{ area: true, dataKey: 'weight' }]}
                        yAxis={[{ scaleType: 'linear' }]}
                        height={400}
                        dataset={maxWeight!}
                    />
                </Container>
            }
        </Container>
    )
}