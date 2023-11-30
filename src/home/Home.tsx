import * as React from 'react';
import { useState, useEffect } from 'react';
import Container from '@mui/material/Container';
import Box from '@mui/material/Box';
import { Calendar, dayjsLocalizer } from 'react-big-calendar';
import { useNavigate } from 'react-router-dom';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';
import 'react-big-calendar/lib/css/react-big-calendar.css';

import CustomToolbar from './CalendarToolbar';
import { WorkoutDto, CalendarEvent } from '../types';
import AddWorkoutModal from '../workouts/allworkouts/AddWorkoutModal';

dayjs.extend(utc);

export default function Home() {
    const navigate = useNavigate();
    const localizer = dayjsLocalizer(dayjs);

    // States for workouts and the calendar events
    const [workouts, setWorkouts] = useState<Array<WorkoutDto>>([]);
    const [events, setEvents] = useState<Array<CalendarEvent>>([]);

    // Trackes the date of the selected cell in the calendar
    const [selectedDate, setSelectedDate] = useState<Dayjs | undefined>();

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

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

            const newEvent: CalendarEvent = { id: workout.id, title: workout.title, start: new Date(year, month, day), end: new Date(year, month, day), rating: workout.rating };
            curEvents.push(newEvent);
        });

        setEvents([...events, ...curEvents]);
    }, [workouts]);

    // Dialog open handler
    const handleOpenAddWorkout = (e : { start : Date}) => {
        setSelectedDate(dayjs(e.start).set('hour', 23));
        setOpenAddWorkout(true);
    };

    // Dialog close handler
    const handleCloseAddWorkout = () => {
        setOpenAddWorkout(false);
    };

    // Workout form submission
    const handleSubmit = (title: string, date: Dayjs | null, notes: string, duration: number) => {
        const workout = { title, date, notes, duration };

        fetch("http://localhost:8080/workouts/new", {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(workout),
            credentials: 'include'
        }).then(() => {
            console.log("New workout added");
        })
        window.location.reload();
    }

    // Redirects the user to the logout page
    const handleLogout = () => {
        navigate("/logout");
    }

    // Navigates to the workout that was clicked    
    const handleClick = (e: CalendarEvent) => {
        navigate("/workouts/" + e.id);
    };

    return (
        <Container style={{ width: "100%", margin: 0, padding: 0, maxWidth: "100%" }}>
            <Box style={{ backgroundColor: "white", borderRadius: 10 }}>
                <Calendar
                    selectable
                    localizer={localizer}
                    events={events}
                    startAccessor="start"
                    endAccessor="end"
                    views={['month']}
                    style={{ height: 500, borderRadius: 5 }}
                    onSelectEvent={handleClick}
                    onSelectSlot={handleOpenAddWorkout}
                    components={{
                        toolbar: CustomToolbar
                    }}
                    eventPropGetter={(event) => {
                        let backgroundColor : string;

                        // Sets the event color based on the rating
                        switch (event.rating) {
                            case 1:
                                backgroundColor = "red";
                                break;
                            case 2:
                                backgroundColor = "#f44336";
                                break;
                            case 3:
                                backgroundColor = "#ffa726";
                                break;
                            case 4:
                                backgroundColor = "#66bb6a";
                                break;
                            case 5:
                                backgroundColor = "green";
                                break;
                            default:
                                backgroundColor = "grey";
                                break;
                        }

                        return { style: { backgroundColor } }
                    }}
                />

                <AddWorkoutModal
                    open={openAddWorkout}
                    handleSubmit={handleSubmit}
                    handleClose={handleCloseAddWorkout}
                    date={selectedDate}
                />
            </Box>
        </Container>
    )
}