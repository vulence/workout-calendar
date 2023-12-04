import React from 'react';
import { useEffect, useState } from 'react';
import {
    Container, Box, Card, CardActions, CardContent, CardHeader, CardActionArea, Fab, Popover, IconButton, Grid, Typography, Rating, Tooltip, Divider
} from '@mui/material';
import { Link } from "react-router-dom";
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers/';
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

import { WorkoutDto, PopoverState, CustomIcons, AllWorkoutsFilters } from '../../types';
import { calculateTotalCaloriesForWorkout } from '../utils/calorieCalculator';
import FilterAccordion from './FilterAccordion';
import { fetchWorkouts } from '../../api/api';

dayjs.extend(utc);

export default function AllWorkouts() {
    // Data states
    const [workouts, setWorkouts] = useState<Array<WorkoutDto>>([]);
    const [filterValues, setFilterValues] = useState<AllWorkoutsFilters>({
        filterYear: null,
        filterMonth: null,
        muscleGroupName: '',
        sortByDate: 'desc'
    });

    // Stores burned calories for each workout
    const [calories, setCalories] = useState<Array<number | undefined>>([]);

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

    // Anchor for the popover
    const [popoverState, setPopoverState] = useState<PopoverState>({});
    const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

    // Initialize all workouts
    useEffect(() => {
        fetchWorkouts().then(data => setWorkouts(data));
    }, [setWorkouts]);

    // Calculates calories burned for each workout
    useEffect(() => {
        const fetchCalories = async () => {
            try {
                const caloriesPromises = workouts.map(async (workout) => {
                    const caloriesBurned = await calculateTotalCaloriesForWorkout(workout.id);
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

    // Applies filters to workouts
    const filterWorkouts = () => {
        let filteredWorkouts = workouts;

        if (filterValues.filterYear !== null) filteredWorkouts = filteredWorkouts.filter(workout => parseInt(workout.date.split('-')[2]) === filterValues.filterYear);
        if (filterValues.filterMonth !== null) filteredWorkouts = filteredWorkouts.filter(workout => parseInt(workout.date.split('-')[1]) === filterValues.filterMonth! + 1);
        if (filterValues.muscleGroupName !== '') filteredWorkouts = filteredWorkouts.filter(workout => workout.muscleGroups.includes(filterValues.muscleGroupName));

        return filteredWorkouts.sort(filterValues.sortByDate === 'desc' ? ((a, b) => a.date > b.date ? -1 : 1) : ((a, b) => a.date > b.date ? 1 : -1));
    }

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

    // Delete a workout
    const handleDelete = (workoutId: number) => {
        fetch(`http://localhost:8080/workouts/${workoutId}`, {
            method: "DELETE",
            credentials: "include"
        }).then(() => {
            console.log("Workout deleted");
        })
        window.location.reload();
    }

    // Add a rating to a workout
    const handleRating = (workoutId: number, rating: number) => {
        // Update the rating in the state
        const updatedWorkouts: Array<WorkoutDto> = workouts.map((w) => {
            if (w.id === workoutId) {
                // Remove the rating if the user clicks on the same rating as it already is
                if (rating === null) {
                    rating = 0; // In order to make a valid API call
                    return { ...w, rating: rating };
                }
                // Set the rating if it's not the same
                else {
                    return { ...w, rating: rating };
                }
            }

            return w;
        });

        setWorkouts(updatedWorkouts);

        // Update the rating in the database
        fetch(`http://localhost:8080/workouts/${workoutId}/setRating`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            body: JSON.stringify(rating)
        }).then(() => {
            console.log("Workout rating successfully modified!");
        })

        handlePopoverClose(workoutId);
    }

    // Checks if the date(string) is in the future
    const isLaterDate = (date: string) => {
        const [day, month, year] = date.split('-').map(value => parseInt(value, 10));
        const selectedDate = dayjs(`${year}-${month}-${day}`);

        return selectedDate.isAfter(dayjs());
    }

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Helmet>
                <title>All Workouts</title>
            </Helmet>

            <Container>
                <FilterAccordion
                    updateParentValues={handleFilterValues}
                />

                <Grid container spacing={2} className={styles.gridContainer}>
                    {filterWorkouts().map((workout, index) => (
                        <Grid key={workout.id} item xs={1} sm={2} md={3} className={styles.gridItem}>
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
                                        <IconButton
                                            aria-owns={popoverState[workout.id] ? `mouse-over-popover-${workout.id}` : undefined}
                                            aria-haspopup="true"
                                            onClick={(e) => handlePopoverOpen(workout.id, e)}
                                            disabled={isLaterDate(workout.date)}
                                        >
                                            {workout.rating ? customIcons[workout.rating].icon : customIcons[0].icon}
                                        </IconButton>
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
                                            onChange={(event, newValue) => handleRating(workout.id, newValue!)}
                                            highlightSelectedOnly
                                        />
                                    </Popover>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}
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