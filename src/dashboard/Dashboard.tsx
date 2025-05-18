import { useState, useEffect } from 'react';
import { Container, Box, Card, CardContent, CardHeader, Typography, Divider, Checkbox, Skeleton } from '@mui/material';
import { Calendar, dayjsLocalizer } from 'react-big-calendar';
import { useNavigate } from 'react-router-dom';
import TimerIcon from '@mui/icons-material/Timer';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';
import isToday from 'dayjs/plugin/isToday';
import 'react-big-calendar/lib/css/react-big-calendar.css';

import CustomToolbar from './CalendarToolbar';
import { Workout, CalendarEvent, WorkoutExercise, CompletedWorkout } from '../types';
import AddWorkoutModal from '../workouts/allworkouts/AddWorkoutModal';
import LinearProgressWithLabel from './LinearProgressWithLabel';
import styles from './dashboard.module.css';
import { fetchCompletedWorkouts, fetchWorkoutExercises, submitWorkout, updateWorkoutExercise } from '../api/api';
import { stringToDayjs } from '../workouts/utils/dateConverter';
import { ThreeDots } from 'react-loader-spinner';

dayjs.extend(utc);
dayjs.extend(isToday);

export default function Dashboard() {
    const navigate = useNavigate();
    const localizer = dayjsLocalizer(dayjs);

    // States for workouts and the calendar events
    const [completedWorkouts, setCompletedWorkouts] = useState<Array<CompletedWorkout>>([]);
    const [events, setEvents] = useState<Array<CalendarEvent>>([]);
    const [todaysWorkout, setTodaysWorkout] = useState<CompletedWorkout | null>(null);
    const [todaysWorkoutExercises, setTodaysWorkoutExercises] = useState<WorkoutExercise[] | null>(null);
    const [loadingTodaysWorkout, setLoadingTodaysWorkout] = useState<boolean>(false);
    const [progress, setProgress] = useState<number>(0);
    const [progressIncrement, setProgressIncrement] = useState<number>(0);

    // Trackes the date of the selected cell in the calendar
    const [selectedDate, setSelectedDate] = useState<Dayjs | undefined>();

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

    const [loading, setLoading] = useState<boolean>(true);

    // Gets all the workouts
    useEffect(() => {
        fetchCompletedWorkouts().then(data => setCompletedWorkouts(data));
    }, []);

    // Creates an event for each of the workout and adds it to the calendar, as well as setting today's workout if it exists
    useEffect(() => {
        setLoading(true);
        const curEvents: CalendarEvent[] = [];

        completedWorkouts.forEach(workout => {
            curEvents.push(createEvent(workout.date, workout.id, workout.workoutId));

            if (stringToDayjs(workout.date).isToday()) {
                setTodaysWorkout(workout);
                initializeTodaysWorkout(workout);
                return;
            }
        });

        setEvents([...events, ...curEvents]);
        setLoading(false);
    }, [completedWorkouts]);

    const createEvent = (stringDate: string, id: number, workoutId: number): CalendarEvent => {
        const parts = stringDate.split("-");
        const year = parseInt(parts[0]);
        const month = parseInt(parts[1]) - 1;
        const day = parseInt(parts[2]);

        return { id: id, start: new Date(year, month, day), end: new Date(year, month, day), workoutId: workoutId, title: "cao" }
    }

    // If there is a today's workout, set all essential elements related to progress
    const initializeProgress = (workoutExercises: WorkoutExercise[]) => {
        const divisor: number = workoutExercises.length > 0 ? workoutExercises.length : 1;
        setProgressIncrement(100 / divisor);

        const countCompletedExercises = workoutExercises.filter(workoutExercise => {
            return workoutExercise.completed;
        }).length;

        setProgress((100 / divisor) * countCompletedExercises);
    }

    // Load in all of the exercises related to today's workout
    const initializeTodaysWorkout = (todaysWorkout: CompletedWorkout) => {
        setLoadingTodaysWorkout(true);

        fetchWorkoutExercises(todaysWorkout.id.toString()).then((workoutExercises) => {
            setTodaysWorkoutExercises(workoutExercises);
            initializeProgress(workoutExercises);
            setLoadingTodaysWorkout(false);
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

    // Navigates to the workout that was clicked    
    const handleClick = (e: CalendarEvent) => {
        navigate("/workouts/" + e.id);
    };

    // Updates the status bar and the workoutExercise that was marked as (not)completed
    const handleExerciseChecked = (workoutExerciseId: number, checked: boolean) => {
        checked ? setProgress(progress + progressIncrement) : setProgress(progress - progressIncrement);

        const workoutExercise = todaysWorkoutExercises!.find(we => we.id === workoutExerciseId)!;
        workoutExercise.completed = !workoutExercise.completed;

        setTodaysWorkoutExercises(prevState => prevState!.map(we => we.id === workoutExerciseId ? workoutExercise : we));
        updateWorkoutExercise(todaysWorkoutExercises![0].workoutId.toString(), workoutExercise).then(data => console.log(data));
    }

    return (
        <Container className={styles.content}>
            <Box className={styles.container}>
                <Box className={styles.calendarContainer}>
                    <Calendar
                        selectable
                        localizer={localizer}
                        events={events}
                        startAccessor="start"
                        endAccessor="end"
                        views={['month']}
                        onSelectEvent={handleClick}
                        onSelectSlot={handleOpenAddWorkout}
                        components={{
                            toolbar: CustomToolbar
                        }}
                    />

                    {loading ? (
                        <Box
                            sx={{
                                position: 'absolute',
                                top: 0,
                                left: 0,
                                width: '100%',
                                height: '100%',
                                background: 'rgba(0, 0, 0, 0.8)',
                                display: 'flex',
                                justifyContent: 'center',
                                alignItems: 'center',
                                zIndex: 999,
                            }}
                        >
                            <ThreeDots
                                visible={true}
                                height="80"
                                width="80"
                                color="white"
                                radius="9"
                                ariaLabel="three-dots-loading"
                                wrapperStyle={{
                                    marginLeft: "30px"
                                }}
                            />
                        </Box>
                    ) : null}
                </Box>

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
                        {loadingTodaysWorkout ? (
                            <>
                                <Skeleton animation="wave" width="80%" height={20} />
                                <Skeleton animation="wave" width="30%" height={20} />
                                <Skeleton animation="wave" variant="circular" width={20} height={20} />
                                <Skeleton animation="wave" width="80%" height={20} />
                            </>
                        ) : (
                            <>
                                <Typography sx={{ fontWeight: "bold" }}>Today's workout overview</Typography>
                                <Divider sx={{ margin: 1 }} />
                                {todaysWorkout && todaysWorkoutExercises ? (
                                    <>
                                        <Box className={styles.todaysWorkoutTitleDuration}>
                                            <Typography>{todaysWorkout.id}</Typography>
                                            <TimerIcon sx={{ marginLeft: "auto" }} />
                                            <Typography alignSelf="center" fontSize={15}>{todaysWorkout.workoutId}min</Typography>
                                        </Box>
                                        <Box className={styles.todaysWorkoutProgress}>
                                            <LinearProgressWithLabel value={progress} />
                                            {todaysWorkoutExercises.map((workoutExercise) => (
                                                <Box
                                                    key={workoutExercise.id}
                                                    className={styles.todaysWorkoutExercise}
                                                    sx={{ backgroundColor: workoutExercise.completed ? "rgb(8, 94, 32, 0.7)" : "rgb(0, 0, 0, 0.7)" }}
                                                >
                                                    <Typography>{workoutExercise.exerciseName}</Typography>
                                                    <Checkbox defaultChecked={workoutExercise.completed} onChange={(e) => handleExerciseChecked(workoutExercise.id, e.target.checked)} />
                                                </Box>
                                            ))}
                                        </Box>
                                    </>
                                ) : (
                                    <Typography>You don't have a workout scheduled for today.</Typography>
                                )}
                            </>
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