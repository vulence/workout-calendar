import React from 'react';
import Box from '@mui/material/Box';
import Select from '@mui/material/Select';
import { Button, FormControl, MenuItem } from '@mui/material';
import { SelectChangeEvent } from '@mui/material/Select';
import dayjs, { Dayjs } from 'dayjs';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './Calendar.css';

export default function CustomToolbar(toolbarProps: { onNavigate: any; label: any; }) {
    const { onNavigate, label } = toolbarProps;

    const handleMonthChange = (event: SelectChangeEvent) => {
        const selectedMonth = parseInt(event.target.value, 10);
        onNavigate('date', dayjs(label).month(selectedMonth).toDate());
    };

    const handleYearChange = (event: SelectChangeEvent) => {
        const selectedYear = parseInt(event.target.value, 10);
        onNavigate('date', dayjs(label).year(selectedYear).toDate());
    };

    return (
        <Box style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 5, padding: 5}}>
            <Button variant="outlined" style={{marginRight: "auto", borderWidth: 1}} onClick={() => onNavigate('PREV')}>{'<'}</Button>
            <Select style={{width: 150}} value={dayjs(label).month().toString()} onChange={handleMonthChange}>
                {Array.from({ length: 12 }, (_, index) => (
                    <MenuItem key={index} value={index}>
                        {dayjs().month(index).format('MMMM')}
                    </MenuItem>
                ))}
            </Select>
            <Select value={dayjs(label).year().toString()} onChange={handleYearChange}>
                {Array.from({ length: 10 }, (_, index) => dayjs(label).year() - index + 1).map((year) => (
                    <MenuItem key={year} value={year}>
                        {year}
                    </MenuItem>
                ))}
            </Select>
            <Button variant="outlined" style={{marginLeft: "auto"}} onClick={() => onNavigate('NEXT')}>{'>'}</Button>
        </Box>
    );
};