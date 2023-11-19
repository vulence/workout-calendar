import * as React from 'react';
import { useEffect, useState } from 'react';
import Container from '@mui/material/Container';
import { DataGrid } from '@mui/x-data-grid';
import { MuscleGroup } from '../types';

export default function AllMuscleGroups() {
    // Data states
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);

    // Initialize data grid columns
    const columns = [
        { field: 'name', headerName: 'Muscle group name', width: 300 },
        { field: 'description', headerName: 'Muscle group description', width: 300 },
    ];

    // Initialize all muscle groups
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/muscleGroups");
            const result = await response.json();
            setMuscleGroups(result);
        };
        fetchData();
    }, [setMuscleGroups])

    return (
        <Container>
            <DataGrid
                rows={muscleGroups}
                columns={columns}
                initialState={{
                    pagination: {
                        paginationModel: { page: 0, pageSize: 15 },
                    },
                }}
                sx={{
                    margin: "10px 0",
                }}
                pageSizeOptions={[10, 15]}
                disableRowSelectionOnClick
                hideFooter
            />
        </Container>
    );
}