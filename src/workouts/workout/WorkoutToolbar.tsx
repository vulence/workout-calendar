import * as React from 'react';
import { useState } from 'react';
import { GridToolbarContainer, GridToolbarFilterButton, GridToolbarExport } from '@mui/x-data-grid';
import Button from '@mui/material/Button';
import NotesIcon from '@mui/icons-material/Notes';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import Box from '@mui/material/Box';
import { MobileTimePicker } from '@mui/x-date-pickers/MobileTimePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import styles from './workoutToolbar.module.css';
import utc from 'dayjs/plugin/utc';
import dayjs, { Dayjs } from 'dayjs';

import { WorkoutToolbarProps } from '../../types';

dayjs.extend(utc);

export default function WorkoutToolbar(props: WorkoutToolbarProps) {
    // Time picker control state
    const [timePickerOpen, setTimePickerOpen] = useState<boolean>(false);

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Box className={styles.dateBox}>
                <h1 className={styles.h1}>{props.workout?.date}</h1>
            </Box>

            <GridToolbarContainer>
                <GridToolbarFilterButton className={styles.Button} />
                <GridToolbarExport className={styles.Button} />
                <Box className={styles.componentBox}>
                    <AccessTimeIcon color="primary" onClick={() => setTimePickerOpen(true)} />
                    <MobileTimePicker ampm={false}
                        open={timePickerOpen}
                        className={styles.mobileTimePicker}
                        onAccept={(e) => props.handleDurationSubmit(e!.minute() + 60 * e!.hour())}
                        onClose={() => setTimePickerOpen(false)}
                        localeText={{ timePickerToolbarTitle: "SET DURATION" } as any}
                        maxTime={dayjs().set('hour', 5)}
                    />

                    {Math.floor(props.workout?.duration / 60)}h {props.workout?.duration - 60 * Math.floor(props.workout?.duration / 60)}min

                    <Button className={styles.Button}
                        variant="contained"
                        startIcon={<NotesIcon />}
                        onClick={props.handleOpenNotesDialog}
                    >
                        NOTES
                    </Button>
                </Box>
            </GridToolbarContainer>
        </LocalizationProvider>
    );
}