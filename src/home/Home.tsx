import { useState, useEffect } from 'react';
import { Container, Box, Card, CardContent, CardHeader, Typography, Divider, Checkbox, Stack } from '@mui/material';
import { Calendar, dayjsLocalizer } from 'react-big-calendar';
import { useNavigate } from 'react-router-dom';
import TimerIcon from '@mui/icons-material/Timer';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';
import isToday from 'dayjs/plugin/isToday';
import 'react-big-calendar/lib/css/react-big-calendar.css';

import CustomToolbar from './CalendarToolbar';
import { Workout, CalendarEvent, Exercise } from '../types';
import AddWorkoutModal from '../workouts/allworkouts/AddWorkoutModal';
import LinearProgressWithLabel from './LinearProgressWithLabel';
import styles from './home.module.css';
import { fetchWorkoutExercises, fetchWorkouts, submitWorkout, updateExerciseDoneCompleted } from '../api/api';
import { stringToDayjs } from '../workouts/utils/dateConverter';

dayjs.extend(utc);
dayjs.extend(isToday);

export default function Home() {
    const navigate = useNavigate();
    const localizer = dayjsLocalizer(dayjs);

    // States for workouts and the calendar events
    const [workouts, setWorkouts] = useState<Array<Workout>>([]);
    const [events, setEvents] = useState<Array<CalendarEvent>>([]);
    const [todaysWorkout, setTodaysWorkout] = useState<Workout | null>(null);
    const [progress, setProgress] = useState<number>(0);
    const [progressIncrement, setProgressIncrement] = useState<number>(0);

    // Trackes the date of the selected cell in the calendar
    const [selectedDate, setSelectedDate] = useState<Dayjs | undefined>();

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

    // Gets all the workouts
    useEffect(() => {
        fetchWorkouts().then(data => setWorkouts(data));
    }, []);

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

    // Sets the today's workout, if it exists and sets progress increment
    useEffect(() => {
        workouts.forEach(workout => {
            if (stringToDayjs(workout.date).isToday()) {
                initializeProgress(workout);
                initializeTodaysWorkout(workout);
                return;
            }
        });
    }, [workouts]);

    // If there is a today's workout, set all essential elements related to progress
    const initializeProgress = (workout: Workout) => {
        const divisor : number = workout.exercisesDone.length > 0 ? workout.exercisesDone.length : 1;
        setProgressIncrement(100 / divisor);

        const countCompletedExercises = workout.exercisesDone.filter(exerciseDone => {
            return exerciseDone.completed;
        }).length;

        setProgress((100 / divisor) * countCompletedExercises);
    }

    // Load in all of the exercises related to today's workout
    const initializeTodaysWorkout = (todaysWorkout: Workout) => {
        fetchWorkoutExercises(todaysWorkout.id.toString()).then((exercises) => {
            setTodaysWorkout({
                ...todaysWorkout,
                exercisesDone: todaysWorkout.exercisesDone.map((exerciseDone) => ({
                    ...exerciseDone,
                    exercise: exercises.find((exercise: Exercise) => exercise.id === exerciseDone.exercise.id),
                })),
            });
        });
    }

    // Dialog open handler
    const handleOpenAddWorkout = (e: { start: Date }) => {
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

        submitWorkout(workout).then(message => console.log(message));

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

    // Updates the status bar and the exercisedone that was marked as (not)completed
    const handleExerciseChecked = (exerciseDoneId: number, checked: boolean) => {
        if (checked) {
            setProgress(progress + progressIncrement);
        }
        else {
            setProgress(progress - progressIncrement);
        }

        const findTargetExercise = todaysWorkout!.exercisesDone.find(ed => ed.id === exerciseDoneId);

        if (findTargetExercise) {
            setTodaysWorkout({
                ...todaysWorkout!,
                exercisesDone: todaysWorkout!.exercisesDone.map(ed => (ed === findTargetExercise ? { ...ed, completed: !ed.completed } : ed))
            });
        }

        updateExerciseDoneCompleted(todaysWorkout!.id.toString(), exerciseDoneId.toString(), checked).then(data => console.log(data));
    }

    return (
        <Container className={styles.content}>
            <Box className={styles.calendarContainer}>
                <Calendar
                    selectable
                    localizer={localizer}
                    events={events}
                    startAccessor="start"
                    endAccessor="end"
                    views={['month']}
                    style={{ height: 500, borderRadius: 5, flex: 3 }}
                    onSelectEvent={handleClick}
                    onSelectSlot={handleOpenAddWorkout}
                    components={{
                        toolbar: CustomToolbar
                    }}
                    eventPropGetter={(event) => {
                        let backgroundColor: string;

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

                <Card variant="outlined" className={styles.cardStats}>
                    <CardHeader
                        title="Your stats"
                        titleTypographyProps={{ fontWeight: "bold", fontSize: 25, color: "grey" }}
                    />
                    <Divider />

                    <CardContent sx={{ display: "flex" }}>
                        <Typography>Workouts in a row: 4</Typography>
                    </CardContent>

                    <CardContent className={styles.workoutOverviewContent}>
                        <Typography sx={{ fontWeight: "bold" }}>Today's workout overview</Typography>
                        <Divider sx={{ margin: 1 }} />
                        {todaysWorkout ? (
                            <>
                                <Box className={styles.todaysWorkoutTitleDuration}>
                                    <Typography>{todaysWorkout.title}</Typography>
                                    <TimerIcon sx={{ marginLeft: "auto" }} />
                                    <Typography alignSelf="center" fontSize={15}>{todaysWorkout.duration}min</Typography>
                                </Box>
                                <Box className={styles.todaysWorkoutProgress}>
                                    <LinearProgressWithLabel value={progress} />
                                    {todaysWorkout.exercisesDone.map((exerciseDone) => (
                                        <Box
                                            key={exerciseDone.id}
                                            className={styles.todaysWorkoutExercise}
                                            sx={{backgroundColor: exerciseDone.completed ? "rgb(8, 94, 32, 0.7)" : "rgb(0, 0, 0, 0.7)"}}
                                        >
                                            <Typography>{exerciseDone.exercise.name}</Typography>
                                            <Checkbox defaultChecked={exerciseDone.completed} onChange={(e) => handleExerciseChecked(exerciseDone.id, e.target.checked)} />
                                        </Box>
                                    ))}
                                </Box>
                            </>
                        ) : (
                            <Typography>You don't have a workout scheduled for today.</Typography>
                        )}
                    </CardContent>
                </Card>

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