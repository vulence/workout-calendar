import { Dayjs } from "dayjs";
import React, { ReactNode } from "react";

export type User = {
    id: string;
    username: string;
    email: string;
}

export type Exercise = {
    id: number;
    name: string;
    description: string;
    imageUrl: string;
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
    title: string;
    date: string;
    notes: string;
    duration: number;
    rating: number;
};

export type WorkoutExercise = {
    id: number;
    exerciseName: string;
    weight: number;
    sets: number;
    reps: number;
    completed: boolean;
    workoutId: number;
    exerciseId: number;
};

export type MuscleGroup = {
    id: number;
    name: string;
    description: string;
    exerciseCount: number;
};

export type ExerciseHistoryToolbarProps = {
    exercise: Exercise;
};

export type AddWorkoutModalProps = {
    open: boolean;
    handleSubmit: (title: string, date: Dayjs | null, notes: string, duration: number) => void;
    handleClose: () => void;
    date?: Dayjs;
};

export type WorkoutToolbarProps = {
    handleOpenNotesDialog: () => void;
    handleDurationSubmit: (minutes : number) => void;
    workout: Workout;
};

export type AddWorkoutExerciseModalProps = {
    open: boolean;
    handleClose: () => void;
    handleSubmit: (exerciseId : number, weight : number, sets : number, reps : number) => void;
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
    completed: boolean;
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
    emailOrUsername: string;
    password: string;
}

export type RegisterFormData = {
    username: string;
    email: string;
    password: string;
    confirmPassword: string;
}

export type AuthContextType = {
    user: User | null;
    authenticated: boolean;
    loading: boolean;
}

export type IconMap = {
    [key : string]: React.ReactNode;
}

export type AuthProviderProps = {
    children: ReactNode;
}

export type CalendarEvent = {
    id: number;
    workoutId: number;
    start: Date;
    end: Date;
    title: string;
}

export type AllWorkoutsFilters = {
    filterYear: number | null;
    filterMonth: number | null;
    sortByDate: string;
}

export type FilterAccordionProps = {
    updateParentValues: (newValues : AllWorkoutsFilters) => void;
}

export type CompletedWorkout = {
    id: number;
    workoutId: number;
    date: string;
}