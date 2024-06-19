package com.vule.workoutcalendar.workoutexercise.api;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;

import java.util.List;

public interface WorkoutExerciseServiceApi {

    /**
     * Retrieves all workout exercises for the user with id userId, for their workout with id workoutId
     *
     * @param userId The id of the user to whom the workout belongs to
     * @param workoutId The id of the workout for which the workout exercises will be retrieved
     * @return A list of WorkoutExercise objects for the particular user and the workout,
     * or an empty list if there are no such WorkoutExercise objects
     */
    List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer workoutId);

    /**
     * Retrieves all workout exercises for the user with id userId, for their workout with id workoutId
     * in the GroupedExercise format, suitable for mobile.
     *
     * @param userId The id of the user to whom the workout belongs to
     * @param workoutId The id of the workout for which the workout exercises will be retrieved
     * @return A list of GroupedExerciseDto objects for the particular user and the workout,
     * or an empty list if there are no such GroupedExerciseDto objects
     */
    List<GroupedExerciseDto> getGroupedWorkoutExercises(Integer userId, Integer workoutId);

    /**
     * Adds a new workout exercise represented by the workoutExerciseDto for the user with id userId, for their workout with id workoutId
     *
     * @param userId The id of the user to whom the workout belongs to
     * @param workoutId The id of the workout to which the new WorkoutExercise will be added
     * @param workoutExerciseDto A dto object that contains all necessary information for the new WorkoutExercise
     */
    void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto);

    /**
     * Updates an existing workoutExercise for the user with id userId, for their workout with id workoutId
     *
     * @param userId The id of the user to whom the workout belongs to
     * @param workoutId The id of the workout to which the WorkoutExercise being updated belongs to
     * @param workoutExercise A workoutExercise object with the updated data
     */
    void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise);

    /**
     * Deletes a workoutExercise with the id workoutExerciseId, for the user with id userId, for their workout with id workoutId
     *
     * @param userId The id of the user to whom the workout belongs to
     * @param workoutId The id of the workout to which the WorkoutExercise belongs to
     * @param WorkoutExerciseId The id of the WorkoutExercise to be deleted
     */
    void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId);
}
