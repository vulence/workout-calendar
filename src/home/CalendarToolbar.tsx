import Box from '@mui/material/Box';
import Select from '@mui/material/Select';
import { Button, MenuItem } from '@mui/material';
import { SelectChangeEvent } from '@mui/material/Select';
import dayjs, { Dayjs } from 'dayjs';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import './Calendar.css';

export default function CustomToolbar(toolbarProps: { onNavigate: any; label: any; }) {
    const { onNavigate, label } = toolbarProps;

    const handleMonthChange = (event: SelectChangeEvent) => {
        const selectedMonth = parseInt(event.target.value, 10);
        onNavigate('date', dayjs(label).month(selectedMonth).toDate());
    };

    const handleYearChange = (event: SelectChangeEvent) => {
        const selectedYear = parseInt(event.target.value, 10);
        onNavigate('date', dayjs(label).year(selectedYear).toDate());
    };

    const resetDate = () => {
        onNavigate('date', dayjs().toDate());
    };

    return (
        <Box style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginBottom: 5, padding: 5 }}>
            <Button variant="contained" style={{ marginRight: "auto", borderWidth: 1, height: 50, backgroundColor: "white" }} onClick={() => onNavigate('PREV')}>{'<'}</Button>
            <Box>
                <Select style={{ width: 150, margin: 5, height: 50, backgroundColor: "black" }} value={dayjs(label).month().toString()} onChange={handleMonthChange}>
                    {Array.from({ length: 12 }, (_, index) => (
                        <MenuItem key={index} value={index}>
                            {dayjs().month(index).format('MMMM')}
                        </MenuItem>
                    ))}
                </Select>
                <Select style={{ margin: 5, height: 50, backgroundColor: "black" }} value={dayjs(label).year().toString()} onChange={handleYearChange}>
                    {Array.from({ length: 5 }, (_, index) => dayjs(label).year() - index + 2).map((year) => (
                        <MenuItem key={year} value={year}>
                            {year}
                        </MenuItem>
                    ))}
                </Select>
                <Button variant="outlined" style={{ display: "flex", width: "100%", color: "red", borderWidth: 1, borderColor: "black"}} onClick={() => resetDate()}>RESET</Button>
            </Box>
            <Button variant="contained" style={{ marginLeft: "auto", height: 50, backgroundColor: "white" }} onClick={() => onNavigate('NEXT')}>{'>'}</Button>
        </Box>
    );
};