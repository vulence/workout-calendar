import * as React from 'react';
import { useState, useEffect } from 'react';
import { Button, FormControl, MenuItem } from '@mui/material';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Select from '@mui/material/Select';
import { SelectChangeEvent } from '@mui/material/Select';
import { Calendar, dayjsLocalizer } from 'react-big-calendar';
import dayjs, { Dayjs } from 'dayjs';
import 'react-big-calendar/lib/css/react-big-calendar.css';

import { AuthContext } from '../auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType, WorkoutDto, CalendarEvent } from '../types';
import { useNavigate } from 'react-router-dom';

export default function Home() {
    const navigate = useNavigate();
    const localizer = dayjsLocalizer(dayjs);

    // States for workouts and the calendar events
    const [workouts, setWorkouts] = useState<Array<WorkoutDto>>([]);
    const [events, setEvents] = useState<Array<CalendarEvent>>([]);

    // Redirects the user to the logout page
    const handleLogout = () => {
        navigate("/logout");
    }

    // Gets all the workouts
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/workouts", {
                method: "GET",
                credentials: 'include',
            });

            const result = await response.json();
            setWorkouts(result);
        };
        fetchData();
    }, [setWorkouts]);

    // Creates an event for each of the workout and adds it to the calendar
    useEffect(() => {
        const curEvents: CalendarEvent[] = [];

        workouts.forEach(workout => {
            const parts = workout.date.split("-");

            const year = parseInt(parts[2]);
            const month = parseInt(parts[1]) - 1;
            const day = parseInt(parts[0]);

            const newEvent: CalendarEvent = { id: workout.id, title: "Workout", start: new Date(year, month, day), end: new Date(year, month, day) };
            curEvents.push(newEvent);
        });

        setEvents([...events, ...curEvents]);
    }, [workouts]);

    // Navigates to the workout that was clicked    
    const handleClick = (e: CalendarEvent) => {
        navigate("/workouts/" + e.id);
    };

    const CustomToolbar: React.FC<any> = (toolbarProps) => {
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
            <Box style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 5}}>
                <Button variant="outlined" style={{marginRight: "auto", borderWidth: 1, borderColor: "black"}} onClick={() => onNavigate('PREV')}>{'<'}</Button>
                <Select style={{color: "black"}} value={dayjs(label).month().toString()} onChange={handleMonthChange}>
                    {Array.from({ length: 12 }, (_, index) => (
                        <MenuItem key={index} value={index}>
                            {dayjs().month(index).format('MMM')}
                        </MenuItem>
                    ))}
                </Select>
                <Select style={{color: "black"}} value={dayjs(label).year().toString()} onChange={handleYearChange}>
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

    return (
        <Container>
            <Box style={{ margin: 16, backgroundColor: "white", borderRadius: 10 }}>
                <Calendar
                    localizer={localizer}
                    events={events}
                    startAccessor="start"
                    endAccessor="end"
                    views={['month']}
                    style={{ height: 300 }}
                    onSelectEvent={handleClick}
                    components={{
                        toolbar: CustomToolbar
                    }}
                />
            </Box>
        </Container>
    )
}