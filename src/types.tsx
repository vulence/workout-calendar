import { Dayjs } from "dayjs";
import React, { ReactNode } from "react";

export type Exercise = {
    id: number;
    name: string;
    description: string;
};

export type ExerciseHistoryDto = {
    id: number;
    date: Date;
    weight: number;
    sets: number;
    reps: number;
};

export type Workout = {
    id: number;
    date: string;
    notes: string;
    duration: number;
    rating: number;
    exercisesDone: Array<ExerciseDone>;
};

export type ExerciseDone = {
    id: number;
    weight: number;
    sets: number;
    reps: number;
    exercise: Exercise;
};

export type WorkoutDto = {
    id: number;
    date: string;
    notes: string;
    duration: number;
    rating: number;
    muscleGroups: Array<string>;
};

export type MuscleGroup = {
    id: number;
    name: string;
    description: string;
};

export type ExerciseHistoryToolbarProps = {
    exercise: Exercise;
};

export type AddWorkoutModalProps = {
    open: boolean;
    handleSubmit: (date: Dayjs | null, notes: string, duration: number) => void;
    handleClose: () => void;
};

export type WorkoutToolbarProps = {
    handleOpenNotesDialog: () => void;
    handleDurationSubmit: (minutes : number) => void;
    workout: Workout;
};

export type AddExerciseDoneModalProps = {
    open: boolean;
    handleClose: () => void;
    handleSubmit: (exerciseId : number, weight : number, sets : number, reps : number) => void;
    exercises: Array<ExerciseDto>;
    name: string;
    weight: number;
    sets: number;
    reps: number;
};

export type EditNotesModalProps = {
    open: boolean;
    handleClose: () => void;
    handleSubmit: (notes : string) => void;
    notes: string;
};

export type ExerciseDto = {
    id: number;
    name: string;
    description: string;
    muscleGroups: Array<MuscleGroup>;
};

export type AllExercisesDataGridRows = {
    id: number;
    name: string;
    description: string;
    muscleGroups: string;
};

export type WorkoutDataGridRows = {
    id: number;
    exercise: string;
    weight: number;
    sets: number;
    reps: number;
};

export type ExpandMoreProps = {
    expand: boolean;
};

export type ExpandedState = {
    [key: number]: boolean;
};

export type PopoverState =  {
    [workoutId: number]: boolean;
};

export type CustomIcons = {
    [key : number]: {
        icon: React.ReactElement;
        label: string;
    };
}

export type LoginFormData = {
    username: string;
    password: string;
}

export type RegisterFormData = {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
}

export type AuthContextType = {
    token: string | null;
    login: (newToken : string) => void;
    logout: () => void;
}

export type AuthProviderProps = {
    children: ReactNode;
}