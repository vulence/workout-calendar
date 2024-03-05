import React from 'react';
import { useEffect, useState } from 'react';
import {
    Container, Box, Card, CardActions, CardContent, CardHeader, CardActionArea, Fab, Popover, IconButton, Grid, Typography, Rating, Tooltip, Divider, Pagination
} from '@mui/material';
import { Link, useSearchParams } from "react-router-dom";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import AddIcon from '@mui/icons-material/Add';
import { Helmet } from 'react-helmet';
import AddWorkoutModal from './AddWorkoutModal';
import styles from './allWorkouts.module.css';
import PropTypes from 'prop-types';
import { styled } from '@mui/material/styles';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import SentimentDissatisfiedIcon from '@mui/icons-material/SentimentDissatisfied';
import SentimentSatisfiedIcon from '@mui/icons-material/SentimentSatisfied';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAltOutlined';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
import LocalFireDepartmentIcon from '@mui/icons-material/LocalFireDepartment';
import { pink } from '@mui/material/colors';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';

import { PopoverState, CustomIcons, AllWorkoutsFilters, Workout } from '../../types';
import { calculateTotalCaloriesForWorkout } from '../utils/calorieCalculator';
import { stringToDayjs, isLaterDate } from '../utils/dateConverter';
import FilterAccordion from './FilterAccordion';
import { deleteWorkout, fetchWorkouts, fetchWorkoutsCount, submitWorkout, updateWorkout } from '../../api/api';
import LoadingModal from '../../common/LoadingModal';

dayjs.extend(utc);

export default function AllWorkouts() {
    const [queryParams, setQueryParams] = useSearchParams();
    const page = parseInt(queryParams.get("page") || '1');

    // Data states
    const [workouts, setWorkouts] = useState<Array<Workout>>([]);
    const [workoutsCount, setWorkoutsCount] = useState<number>(0);
    const [filterValues, setFilterValues] = useState<AllWorkoutsFilters>({
        filterYear: null,
        filterMonth: null,
        sortByDate: 'desc'
    });

    // Stores burned calories for each workout
    const [calories, setCalories] = useState<Array<number | undefined>>([]);

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

    // Anchor for the popover
    const [popoverState, setPopoverState] = useState<PopoverState>({});
    const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

    const [loading, setLoading] = useState<boolean>(false);

    // Initialize workout count to set the number of pages
    useEffect(() => {
        setLoading(true);
        fetchWorkoutsCount().then((data) => { setWorkoutsCount(data); setLoading(false); });
    }, []);

    // Initialize all workouts
    useEffect(() => {
        setLoading(true);
        fetchWorkouts(page.toString(), filterValues.sortByDate).then((data) => {setWorkouts(data); setLoading(false); });
    }, [page, filterValues]);

    // Calculates calories burned for each workout
    useEffect(() => {
        const fetchCalories = async () => {
            try {
                const caloriesPromises = workouts.map(async (workout) => {
                    const caloriesBurned = await calculateTotalCaloriesForWorkout(workout);
                    return caloriesBurned;
                });

                const caloriesResults = await Promise.all(caloriesPromises);
                setCalories(caloriesResults);
            }
            catch (error) {
                console.error(error);
            }
        };

        fetchCalories();
    }, [workouts]);

    // Sets up rating icons
    const customIcons: CustomIcons = {
        0: {
            icon: <SentimentSatisfiedIcon />,
            label: 'default',
        },
        1: {
            icon: <SentimentVeryDissatisfiedIcon sx={{ color: "red" }} />,
            label: 'Very Dissatisfied',
        },
        2: {
            icon: <SentimentDissatisfiedIcon color="error" />,
            label: 'Dissatisfied',
        },
        3: {
            icon: <SentimentSatisfiedIcon color="warning" />,
            label: 'Neutral',
        },
        4: {
            icon: <SentimentSatisfiedAltIcon color="success" />,
            label: 'Satisfied',
        },
        5: {
            icon: <SentimentVerySatisfiedIcon sx={{ color: "green" }} />,
            label: 'Very Satisfied',
        },
    };

    // Creates the styled rating component
    const StyledRating = styled(Rating)(({ theme }) => ({
        '& .MuiRating-iconEmpty .MuiSvgIcon-root': {
            color: theme.palette.action.disabled,
        },
    }));

    // Creates the rating icon container
    const IconContainer: React.FC<{ value: number }> = (props) => {
        const { value, ...other } = props;
        return <span {...other}>{customIcons[value].icon}</span>;
    }
    IconContainer.propTypes = {
        value: PropTypes.number.isRequired,
    };

    // Filter callback function 
    const handleFilterValues = (newFilterValues: AllWorkoutsFilters) => {
        setFilterValues(newFilterValues);
    }

    // Popover open handler
    const handlePopoverOpen = (workoutId: number, event: React.MouseEvent<HTMLElement>) => {
        setPopoverState({ ...popoverState, [workoutId]: true });
        setAnchorEl(event.currentTarget);
    };

    // Popover close handler
    const handlePopoverClose = (workoutId: number) => {
        setPopoverState({ ...popoverState, [workoutId]: false });
        setAnchorEl(null);
    };

    // Dialog open handler
    const handleOpenAddWorkout = () => {
        setOpenAddWorkout(true);
    }

    // Dialog close handler
    const handleCloseAddWorkout = () => {
        setOpenAddWorkout(false);
    }

    // Workout form submission
    const handleSubmit = (title: string, date: Dayjs | null, notes: string, duration: number) => {
        const workout : any = { title, date, notes, duration };

        submitWorkout(workout).then((message) => {console.log(message); window.location.reload();});
    }

    // Delete a workout
    const handleDelete = (workoutId: number) => {
        deleteWorkout(workoutId.toString()).then((data) => {console.log(data); window.location.reload();});
    }

    // Add a rating to a workout
    const handleRating = (workoutId: number, rating: number) => {   
        const updatedWorkouts: Array<Workout> = workouts.map((w) => {
            if (w.id === workoutId) {
                // Remove the rating if the user clicks on the same rating as it already is
                const updatedRating = rating === null ? 0 : rating;
                return { ...w, rating: updatedRating };
            }

            return w;
        });

        setWorkouts(updatedWorkouts);

        const updatedWorkout = updatedWorkouts.find((w) => w.id === workoutId)!;
        updateWorkout(workoutId.toString(), updatedWorkout).then((data) => console.log(data));

        handlePopoverClose(workoutId);
    }

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Helmet>
                <title>All Workouts</title>
            </Helmet>

            <Container>
                <LoadingModal isLoading={loading} />

                <FilterAccordion
                    updateParentValues={handleFilterValues}
                />
                
                <Grid container spacing={2} className={styles.gridContainer}>
                    {workouts.map((workout, index) => (
                        <Grid key={workout.id} item xs={6} sm={4} md={3} className={styles.gridItem} direction="column" alignItems="center" justifyContent="center">
                            <Card key={workout.id.toString()} variant="outlined">
                                <Tooltip title="Click to see more" placement="top" arrow>
                                    <CardActionArea component={Link} to={workout.id.toString()}>
                                        <CardHeader
                                            title={workout.title}
                                            subheader={workout.date}
                                        />
                                        <Divider />

                                        <CardContent sx={{ textAlign: "left" }}>
                                            <Box sx={{ display: "flex", flexDirection: "row" }}>
                                                <LocalFireDepartmentIcon sx={{ marginRight: 1 }} />
                                                <Typography fontSize={15}>Calories burned: {calories[index] !== null ? calories[index] : 'Loading...'}</Typography>
                                            </Box>
                                        </CardContent>
                                        <Divider />

                                        <CardContent>
                                            <Typography variant="body2" color="text.secondary">
                                                {"Duration: " + Math.floor(workout.duration / 60) + "hr " + (workout.duration - 60 * Math.floor(workout.duration / 60)) + "min"}
                                            </Typography>
                                        </CardContent>
                                    </CardActionArea>
                                </Tooltip>
                                <CardActions>
                                    <Tooltip title="Delete workout" arrow>
                                        <IconButton onClick={() => handleDelete(workout.id)} sx={{ marginRight: 'auto' }}>
                                            <DeleteIcon sx={{ color: pink[500] }} />
                                        </IconButton>
                                    </Tooltip>

                                    <Tooltip title={workout.rating ? "Rating: " + workout.rating + "/5" : "Rate this workout"} arrow>
                                        <span>
                                            <IconButton
                                                aria-owns={popoverState[workout.id] ? `mouse-over-popover-${workout.id}` : undefined}
                                                aria-haspopup="true"
                                                onClick={(e) => handlePopoverOpen(workout.id, e)}
                                                disabled={isLaterDate(workout.date)}
                                            >
                                                {workout.rating ? customIcons[workout.rating].icon : customIcons[0].icon}
                                            </IconButton>
                                        </span>
                                    </Tooltip>
                                    <Popover
                                        id={`mouse-over-popover-${workout.id}`}
                                        open={popoverState[workout.id] || false}
                                        anchorEl={anchorEl}
                                        anchorOrigin={{
                                            vertical: 'bottom',
                                            horizontal: 'left',
                                        }}
                                        transformOrigin={{
                                            vertical: 'top',
                                            horizontal: 'left',
                                        }}
                                        onClose={() => handlePopoverClose(workout.id)}
                                    >
                                        <StyledRating
                                            name="unique-rating"
                                            value={workout.rating || 0}
                                            IconContainerComponent={IconContainer}
                                            onChange={(_, newValue) => handleRating(workout.id, newValue!)}
                                            highlightSelectedOnly
                                        />
                                    </Popover>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}

                    <Box className={styles.paginationBox}>
                        <Pagination variant="outlined" size="large" page={page} count={Math.ceil(workoutsCount / 12)} onChange={(_, page) => setQueryParams(`?page=${page}`)} />
                    </Box>
                </Grid>

                <Fab className={styles.fab} color="primary" variant="extended" aria-label="add" onClick={handleOpenAddWorkout}>
                    <AddIcon sx={{ mr: 1 }} />
                    Add Workout
                </Fab>

                <AddWorkoutModal
                    open={openAddWorkout}
                    handleSubmit={handleSubmit}
                    handleClose={handleCloseAddWorkout}
                />
            </Container>
        </LocalizationProvider>
    )
}