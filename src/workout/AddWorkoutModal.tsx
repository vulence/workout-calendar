import React from 'react';
import {useState} from 'react';
import Button from '@mui/material/Button'
import TextField from '@mui/material/TextField';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import FormHelperText from '@mui/material/FormHelperText';
import FormControl from '@mui/material/FormControl';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';

import { AddWorkoutModalProps } from '../types';

dayjs.extend(utc);

export default function AddWorkoutModal(props : AddWorkoutModalProps) {
    // Data states
    const [date, setDate] = useState<Dayjs | null>(dayjs.utc());
    const [notes, setNotes] = useState<string>('');
    const [hours, setHours] = useState<number>(0);
    const [minutes, setMinutes] = useState<number>(0);

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Dialog open={props.open} onClose={props.handleClose}>
                <DialogTitle margin="10px">Add workout</DialogTitle>
                    <DialogContent>
                        <DatePicker
                            label="Select the workout date"
                            value={date!}
                            format='DD/MM/YYYY'
                            sx= {{ margin: "10px 0"}}
                            onChange={(newDate: Dayjs | null) => setDate(newDate)}
                        />
                        <TextField
                            autoFocus
                            margin="dense"
                            id="workout-notes"
                            label="Workout notes"
                            value={notes}
                            sx= {{ margin: "10px 0"}}
                            multiline
                            fullWidth
                            variant="standard"
                            onChange={(e) => setNotes(e.target.value)}
                        />

                        <InputLabel sx={{margin: "12px 0"}}>Workout duration</InputLabel>
                        <FormControl>
                            <Select
                                sx={{ minWidth: 80, marginRight: "10px"}}
                                id="workout-duration-hours"
                                value={hours}
                                label="Hours"
                                onChange={(e) => setHours(+e.target.value)}
                            >
                                {[...Array.from(Array(5).keys())].map((num, i) =>
                                    <MenuItem value={i + 1}>{i + 1}</MenuItem>
                                )}
                            </Select>
                            <FormHelperText>Hours</FormHelperText>
                        </FormControl>

                        <FormControl>
                            <Select
                                sx={{ minWidth: 80}}
                                id="workout-duration-minutes"
                                value={minutes}
                                label="Minutes"
                                onChange={(e) => setMinutes(+e.target.value)}
                            >
                                {[...Array.from(Array(60).keys())].map((num, i) =>
                                    <MenuItem value={i}>{i}</MenuItem>
                                )}
                            </Select>
                            <FormHelperText>Minutes</FormHelperText>
                        </FormControl>

                        </DialogContent>
                        <DialogActions>
                            <Button onClick={props.handleClose} variant="contained" color="error">Cancel</Button>
                            <Button onClick={() => props.handleSubmit(date, notes, hours*60+minutes)} variant="contained" color="success">Submit</Button>
                        </DialogActions>
            </Dialog>
        </LocalizationProvider>
    );
}