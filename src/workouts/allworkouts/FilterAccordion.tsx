import { useEffect, useState } from 'react';
import {
    Button, Box, MenuItem, Select, Accordion, AccordionSummary, AccordionDetails, AccordionActions, Typography
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import styles from './allWorkouts.module.css';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';
import { MuscleGroup, AllWorkoutsFilters, FilterAccordionProps } from '../../types';

dayjs.extend(utc);

export default function FilterAccordion(props : FilterAccordionProps) {
    // Data states
    const [muscleGroups, setMuscleGroups] = useState<Array<MuscleGroup>>([]);
    const [formValues, setFormValues] = useState<AllWorkoutsFilters>({
        filterYear: null,
        filterMonth: null,
        muscleGroupName: '',
    });

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
    }, [setMuscleGroups]);

    // Updates the form values state
    const handleChange = (id : string, value : number | string) => {
        const newValues = ({ ...formValues, [id]: value });

        setFormValues(newValues);
        props.updateParentValues(newValues);
    };

    // Removes all filters
    const removeFilters = () => {
        const newValues = ({ filterYear: null, filterMonth: null, muscleGroupName: '' });

        setFormValues(newValues);
        props.updateParentValues(newValues);
    }

    return (
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
                        <DatePicker value={formValues.filterYear ? dayjs().set('year', formValues.filterYear) : null} className={styles.datePicker} label={'year'} views={['year']} onAccept={(e) => handleChange("filterYear", e!.year())} />
                        <DatePicker value={formValues.filterMonth ? dayjs().set('month', formValues.filterMonth) : null} className={styles.datePicker} label={'month'} views={['month']} onAccept={(e) => handleChange("filterMonth", e!.month())} />
                    </Box>
                    <Box className={styles.childBox}>
                        <Typography>Filter by muscle group</Typography>
                        <Select
                            id="muscle-group-name"
                            value={formValues.muscleGroupName}
                            onChange={(e) => handleChange("muscleGroupName", e.target.value)}
                            sx={{
                                width: "200px"
                            }}
                        >
                            <MenuItem value=""><em>None</em></MenuItem>
                            {muscleGroups.map(muscleGroup =>
                                <MenuItem key={muscleGroup.id} value={muscleGroup.name}>{muscleGroup.name}</MenuItem>
                            )}
                        </Select>
                    </Box>
                </Box>
            </AccordionDetails>
            <AccordionActions className={styles.accordionActions}>
                <Button variant="contained" color="error" onClick={removeFilters}>Remove all</Button>
            </AccordionActions>
        </Accordion>
    );
}