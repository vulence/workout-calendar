import React from 'react';
import { useEffect, useState } from 'react';
import Container from '@mui/material/Container';
import { Link } from "react-router-dom";
import Button from '@mui/material/Button'
import Box from '@mui/material/Box';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import CardHeader from '@mui/material/CardHeader';
import CardMedia from '@mui/material/CardMedia';
import { CardActionArea, MenuItem, Select } from '@mui/material';
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import Fab from '@mui/material/Fab';
import AddIcon from '@mui/icons-material/Add';
import Popover from '@mui/material/Popover';
import IconButton from '@mui/material/IconButton';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import Grid from '@mui/material/Grid';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionActions from '@mui/material/AccordionActions';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Typography from '@mui/material/Typography';
import { Helmet } from 'react-helmet';
import AddWorkoutModal from './AddWorkoutModal';
import styles from './allWorkouts.module.css';
import PropTypes from 'prop-types';
import { styled } from '@mui/material/styles';
import Rating from '@mui/material/Rating';
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import SentimentDissatisfiedIcon from '@mui/icons-material/SentimentDissatisfied';
import SentimentSatisfiedIcon from '@mui/icons-material/SentimentSatisfied';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAltOutlined';
import SentimentVerySatisfiedIcon from '@mui/icons-material/SentimentVerySatisfied';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';

import { MuscleGroup, WorkoutDto, PopoverState, CustomIcons } from '../types';

dayjs.extend(utc);

export default function AllWorkouts() {
    // Data states
    const [workouts, setWorkouts] = useState<Array<WorkoutDto>>([]);
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);
    const [muscleGroupName, setMuscleGroupName] = useState<string>('');
    const [filterYear, setFilterYear] = useState<number | null>(null);
    const [filterMonth, setFilterMonth] = useState<number | null>(null);

    // Dialog control state
    const [openAddWorkout, setOpenAddWorkout] = useState<boolean>(false);

    // Anchor for the popover
    const [popoverState, setPopoverState] = useState<PopoverState>({});
    const [anchorEl, setAnchorEl] = useState<HTMLElement | null>(null);

    // Sets up rating icons
    const customIcons : CustomIcons = {
        0: {
            icon: <SentimentSatisfiedIcon />,
            label: 'default',
        },
        1: {
            icon: <SentimentVeryDissatisfiedIcon color="error" />,
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
            icon: <SentimentVerySatisfiedIcon color="success" />,
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
    const IconContainer : React.FC<{value : number}> = (props) => {
        const { value, ...other } = props;
        return <span {...other}>{customIcons[value].icon}</span>;
    }
    IconContainer.propTypes = {
        value: PropTypes.number.isRequired,
    };

    // Popover open handler
    const handlePopoverOpen = (workoutId : number, event : React.MouseEvent<HTMLElement>) => {
        setPopoverState({ ...popoverState, [workoutId]: true });
        setAnchorEl(event.currentTarget);
    };

    // Popover close handler
    const handlePopoverClose = (workoutId : number) => {
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

    // Initialize all workouts
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
    }, [setWorkouts])

    // Initialize all muscle groups
    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/muscleGroups", {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
            });
            const result = await response.json();
            setMuscleGroups(result);
        };
        fetchData();
    }, [setMuscleGroups])

    // Workout form submission
    const handleSubmit = (date : Dayjs | null, notes : string, duration : number) => {
        const workout = { date, notes, duration };

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
    const handleDelete = (workoutId : number) => {
        fetch(`http://localhost:8080/workouts/${workoutId}`, {
            method: "DELETE",
            credentials: "include"
        }).then(() => {
            console.log("Workout deleted");
        })
        window.location.reload();
    }

    // Add a rating to a workout
    const handleRating = (workoutId : number, rating : number) => {
        // Update the rating in the state
        const updatedWorkouts : Array<WorkoutDto> = workouts.map((w) => {
            if (w.id === workoutId) {
                return { ...w, rating: rating };
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

    // Applies filters to workouts
    const filterWorkouts = () => {
        let filteredWorkouts = workouts;

        if (filterYear != null) filteredWorkouts = filteredWorkouts.filter(workout => parseInt(workout.date.split('-')[2]) === filterYear);
        if (filterMonth != null) filteredWorkouts = filteredWorkouts.filter(workout => parseInt(workout.date.split('-')[1]) === filterMonth);
        if (muscleGroupName !== '') filteredWorkouts = filteredWorkouts.filter(workout => workout.muscleGroups.includes(muscleGroupName));

        return filteredWorkouts;
    }

    // Removes all filters
    const removeFilters = () => {
        setFilterYear(null);
        setFilterMonth(null);
        setMuscleGroupName('');
    }

    return (
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <Helmet>
                <title>All Workouts</title>
            </Helmet>

            <Container>
                <Accordion disableGutters={true} className={styles.accordion}>
                    <AccordionSummary
                        className={styles.accordionSummary}
                        expandIcon={<ExpandMoreIcon sx={{ color: 'white' }} />}
                        aria-controls="panel1a-content"
                        id="panel1a-header"
                    >
                        <FilterAltIcon className={styles.filterIcon} />
                        <Typography> FILTER </Typography>
                    </AccordionSummary>
                    <AccordionDetails className={styles.accordionDetails}>
                        <Box className={styles.containerBox}>
                            <Box className={styles.childBox}>
                                <Typography>Filter by date</Typography>
                                <DatePicker value={filterYear ? dayjs().set('year', filterYear) : null} className={styles.datePicker} label={'year'} views={['year']} onAccept={(e) => setFilterYear(e!.year())} />
                                <DatePicker value={filterMonth ? dayjs().set('month', filterMonth) : null} className={styles.datePicker} label={'month'} views={['month']} onAccept={(e) => setFilterMonth(e!.month() + 1)} />
                            </Box>
                            <Box className={styles.childBox}>
                                <Typography>Filter by muscle group</Typography>
                                <Select
                                    id="muscle-group-name"
                                    value={muscleGroupName}
                                    onChange={(e) => setMuscleGroupName(e.target.value)}
                                    sx={{
                                        width: "200px"
                                    }}
                                >
                                    <MenuItem value=""><em>None</em></MenuItem>
                                    {muscleGroups.map(muscleGroup =>
                                        <MenuItem value={muscleGroup.name}>{muscleGroup.name}</MenuItem>
                                    )}
                                </Select>
                            </Box>
                        </Box>
                    </AccordionDetails>
                    <AccordionActions className={styles.accordionActions}>
                        <Button variant="contained" color="error" onClick={removeFilters}>Remove all</Button>
                    </AccordionActions>
                </Accordion>

                <Grid container spacing={2} className={styles.gridContainer}>
                    {filterWorkouts().sort((a, b) => a.date > b.date ? -1 : 1).map(workout => (
                        <Grid item xs={1} sm={2} md={3} className={styles.gridItem}>
                            <Card key={workout.id.toString()} variant="outlined">
                                <CardActionArea component={Link} to={workout.id.toString()}>
                                    <CardHeader
                                        title={workout.muscleGroups.join(', ') + " workout"}
                                        subheader={workout.date}
                                    />
                                    <CardMedia
                                        component="img"
                                        height="150"
                                        image={'../img/workhard.jpg'}
                                        title="work hard"
                                    />
                                    <CardContent>
                                        <Typography variant="body2" color="text.secondary">
                                            {"Duration: " + Math.floor(workout.duration / 60) + "hr " + (workout.duration - 60 * Math.floor(workout.duration / 60)) + "min"}
                                        </Typography>
                                    </CardContent>
                                </CardActionArea>
                                <CardActions>
                                    <IconButton onClick={() => handleDelete(workout.id)} sx={{ marginRight: 'auto' }}>
                                        <DeleteIcon />
                                    </IconButton>

                                    <IconButton
                                        aria-owns={popoverState[workout.id] ? `mouse-over-popover-${workout.id}` : undefined}
                                        aria-haspopup="true"
                                        onClick={(e) => handlePopoverOpen(workout.id, e)}
                                    >
                                        {workout.rating ? customIcons[workout.rating].icon : customIcons[0].icon}
                                    </IconButton>
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