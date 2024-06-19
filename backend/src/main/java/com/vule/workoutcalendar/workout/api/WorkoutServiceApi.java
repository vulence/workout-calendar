package com.vule.workoutcalendar.workout.api;

import com.vule.workoutcalendar.workout.Workout;

import java.util.List;

/**
 * Represents an API for the workout service.
 *
 * Workouts can be retrieved according to different parameters, added, deleted or updated.
 *
 * @author vulence
 */
public interface WorkoutServiceApi {

    /**
     * Retrieves all the workouts in a paged format.
     *
     * @param userId Id of the user to whom the workouts belongs to
     * @param page The page which is currently retrieved.
     * @param size The amount of workouts retrieved for each page
     * @param direction The sorting direction (ascending or descending)
     *
     * @return A list of workouts for a particular user of a particular page, or an empty list if the user has no workouts.
     */
    List<Workout> findAllPaged(Integer userId, Integer page, Integer size, String direction);

    /**
     * Retrieves a workout with the supplied ID.
     *
     * @param id The id of a particular workout
     * @param userId Id of the user to whom the workout belongs to
     *
     * @return A workout with the supplied id for the user with userId, or null if it doesn't exist
     */
    Workout findById(Integer id, Integer userId);

    /**
     * Retrieves a workout with the current date.
     *
     * @param userId Id of the user for whom the today's workout should be found
     *
     * @return A workout with today's date for the user with ID userID, null otherwise
     */
    Workout findTodaysWorkout(Integer userId);

    /**
     * Retrieves the numbers of workouts for a particular user.
     *
     * @param userId Id of the user for whom the workouts should be counted
     *
     * @return An integer with the number of workouts for the user with ID userId
     */
    Integer getWorkoutCount(Integer userId);

    /**
     * Adds a new workout.
     *
     * The new workout and the userId should not be null.
     *
     * @param userId Id of the user to whom the workout belongs to
     * @param workout The new workout which is being added
     */
    void create(Integer userId, Workout workout);

    /**
     * Updates an existing workout.
     *
     * The new workout and the userId should not be null, and the workout with workoutId should exist.
     *
     * @param userId Id of the user to whom the workout belongs to
     * @param workoutId Id of the workout which is being updated
     * @param workout The workout containing updated information
     */
    void update(Integer userId, Integer workoutId, Workout workout);

    /**
     * Deletes a workout.
     *
     * The id of the workout and the userId should not be null.
     *
     * @param userId Id of the user to whom the workout belongs to
     * @param id Id of the workout that should be deleted
     */
    void delete(Integer userId, Integer id);
}
