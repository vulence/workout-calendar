import { fetchWorkoutById } from "../../api/api";
import { Workout, ExerciseDone } from "../../types";

// Random constant for calculating calorie expenditure
const CALORIE_CONSTANT = 0.1;

// Gets the workout in order to retreive exercises
const getWorkout = async (workoutId : number) => {
    const workout : Workout = await fetchWorkoutById(workoutId.toString());

    return workout;
};

// Calculates burned calories per exercise
const calculateCaloriesPerExercise = (weight : number, sets : number, reps : number, duration : number) : number => {
    return CALORIE_CONSTANT * weight * (duration / 60) * sets * reps;
}

// Sums up calories for the whole workout
export const calculateTotalCaloriesForWorkout = async (workoutId: number) : Promise<number> => {
    const workout : Workout = await getWorkout(workoutId);

    const timePerExercise : number = workout.duration / workout.exercisesDone.length;
    let totalCalories : number = 0;

    workout.exercisesDone.forEach(exerciseDone => {
        totalCalories += calculateCaloriesPerExercise(exerciseDone.weight, exerciseDone.sets, exerciseDone.reps, timePerExercise);
    });

    return Math.round(totalCalories);
}