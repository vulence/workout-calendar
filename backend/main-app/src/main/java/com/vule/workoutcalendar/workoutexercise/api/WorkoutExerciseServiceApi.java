package com.vule.workoutcalendar.workoutexercise.api;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;

import java.util.List;

public interface WorkoutExerciseServiceApi {

    /**
     * Retrieves all workout exercises for the workout with id workoutId
     *
     * @param workoutId The id of the workout for which the workout exercises will be retrieved
     * @return A list of WorkoutExercise objects for the particular user and the workout,
     * or an empty list if there are no such WorkoutExercise objects
     */
    List<WorkoutExercise> getWorkoutExercises(Integer workoutId);

    /**
     * Adds a new workout exercise workoutExercise
     *
     * @param workoutExercise A new WorkoutExercise object
     */
    void addWorkoutExercise(WorkoutExercise workoutExercise);

    /**
     * Deletes a workoutExercise with the id workoutExerciseId
     *
     * @param WorkoutExerciseId The id of the WorkoutExercise to be deleted
     */
    void deleteWorkoutExercise(Integer WorkoutExerciseId);
}
