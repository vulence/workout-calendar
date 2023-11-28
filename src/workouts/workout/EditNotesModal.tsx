import * as React from 'react';
import { useState, useEffect } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

import { EditNotesModalProps } from '../../types';

export default function EditNotesModal(props : EditNotesModalProps) {
    const [notes, setNotes] = useState<string>(props.notes);

    // Call the parent submit handler
    const handleSubmit = () => {
        props.handleSubmit(notes);
    };

    // Synchronize notes with the parent state
    useEffect(() => {
        setNotes(props.notes);
    }, [props.notes]);

    return (
        <Dialog maxWidth="lg" fullWidth open={props.open} onClose={props.handleClose}>
            <DialogTitle margin="10px">Workout notes</DialogTitle>
            <DialogContent>
                <TextField
                    fullWidth
                    multiline
                    minRows = "15"
                    id="notes"
                    value={notes}
                    onChange={(e) => setNotes(e.target.value) }
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={props.handleClose} variant="contained" color="error">Cancel</Button>
                <Button onClick={handleSubmit} variant="contained" color="success">Update notes</Button>
            </DialogActions>
        </Dialog>
    );
}