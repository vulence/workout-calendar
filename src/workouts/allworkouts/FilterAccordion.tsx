import { useEffect, useState } from 'react';
import {
    Button, Box, MenuItem, Select, Accordion, AccordionSummary, AccordionDetails, AccordionActions, Typography, Switch, Stack, ToggleButtonGroup, ToggleButton
} from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers/';
import FilterAltIcon from '@mui/icons-material/FilterAlt';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import NorthIcon from '@mui/icons-material/North';
import SouthIcon from '@mui/icons-material/South';
import styles from './allWorkouts.module.css';
import dayjs, { Dayjs } from 'dayjs';
import utc from 'dayjs/plugin/utc';
import { AllWorkoutsFilters, FilterAccordionProps } from '../../types';

dayjs.extend(utc);

export default function FilterAccordion(props: FilterAccordionProps) {
    const [formValues, setFormValues] = useState<AllWorkoutsFilters>({
        filterYear: null,
        filterMonth: null,
        sortByDate: "desc"
    });

    // Updates the form values state
    const handleChange = (id: string, value: number | string) => {
        const newValues = ({ ...formValues, [id]: value });

        if (value) {
            setFormValues(newValues);
            props.updateParentValues(newValues);
        }
    };

    // Removes all filters
    const removeFilters = () => {
        const newValues = ({ filterYear: null, filterMonth: null, muscleGroupName: '', sortByDate: formValues.sortByDate});

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

                        <Typography sx={{marginTop: 2}}>Sort by date</Typography>
                        <ToggleButtonGroup
                            color="primary"
                            value={formValues.sortByDate}
                            exclusive
                            onChange={(_, newSortByDate) => handleChange("sortByDate", newSortByDate)}
                            sx={{ display: "block" }}
                        >
                            <ToggleButton value="asc">
                                <Typography>ASC</Typography>
                                <NorthIcon />
                            </ToggleButton>
                            <ToggleButton value="desc">
                                <Typography>DESC</Typography>
                                <SouthIcon />
                            </ToggleButton>
                        </ToggleButtonGroup>

                    </Box>
                </Box>
            </AccordionDetails>
            <AccordionActions className={styles.accordionActions}>
                <Button variant="contained" color="error" onClick={removeFilters}>Remove all</Button>
            </AccordionActions>
        </Accordion>
    );
}