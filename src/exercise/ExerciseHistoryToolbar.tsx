import React from 'react';
import { useState } from 'react';
import { GridToolbarContainer, GridToolbarFilterButton, GridToolbarExport } from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import styles from './exerciseHistoryToolbar.module.css';

import { ExerciseHistoryToolbarProps } from '../types';

export default function ExerciseHistoryToolbar(props : ExerciseHistoryToolbarProps) {
    return (
        <Box className={styles.box}>
            <h1 className={styles.h1}>
                {props.exercise.name}
            </h1>
        </Box>
    );
}