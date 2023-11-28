import * as React from 'react';
import { useState, useEffect } from 'react';
import { Button, FormControl, MenuItem } from '@mui/material';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import Select from '@mui/material/Select';
import { SelectChangeEvent } from '@mui/material/Select';
import { Calendar, dayjsLocalizer } from 'react-big-calendar';
import { useNavigate } from 'react-router-dom';
import dayjs, { Dayjs } from 'dayjs';
import 'react-big-calendar/lib/css/react-big-calendar.css';

import CustomToolbar from './CalendarToolbar';
import { AuthContext } from '../auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType, WorkoutDto, CalendarEvent } from '../types';

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

            const newEvent: CalendarEvent = { id: workout.id, title: workout.title, start: new Date(year, month, day), end: new Date(year, month, day) };
            curEvents.push(newEvent);
        });

        setEvents([...events, ...curEvents]);
    }, [workouts]);

    // Navigates to the workout that was clicked    
    const handleClick = (e: CalendarEvent) => {
        navigate("/workouts/" + e.id);
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
                    style={{ height: 400, borderRadius: 5 }}
                    onSelectEvent={handleClick}
                    components={{
                        toolbar: CustomToolbar
                    }}
                />
            </Box>
        </Container>
    )
}