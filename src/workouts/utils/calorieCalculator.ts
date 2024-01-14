import { fetchWorkoutExercises } from "../../api/api";
import { Workout, WorkoutExercise } from "../../types";

// Random constant for calculating calorie expenditure
const CALORIE_CONSTANT = 0.1;

// Gets the workoutexercises
const getWorkoutExercises = async (workoutId : number) => {
    const workoutExercises : WorkoutExercise[] = await fetchWorkoutExercises(workoutId.toString());

    return workoutExercises;
};

// Calculates burned calories per exercise
const calculateCaloriesPerExercise = (weight : number, sets : number, reps : number, duration : number) : number => {
    return CALORIE_CONSTANT * weight * (duration / 60) * sets * reps;
}

// Sums up calories for the whole workout
export const calculateTotalCaloriesForWorkout = async (workout : Workout) : Promise<number> => {
    const workoutExercises : WorkoutExercise[] = await getWorkoutExercises(workout.id);

    const timePerExercise : number = workout.duration / workoutExercises.length;
    let totalCalories : number = 0;

    workoutExercises.forEach(workoutExercise => {
        totalCalories += calculateCaloriesPerExercise(workoutExercise.weight, workoutExercise.sets, workoutExercise.reps, timePerExercise);
    });

    return Math.round(totalCalories);
}