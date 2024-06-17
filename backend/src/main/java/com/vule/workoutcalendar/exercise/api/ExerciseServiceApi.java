package com.vule.workoutcalendar.exercise.api;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

/**
 * Represents an API for the Exercise service.
 *
 * Exercises can be retrieved according to different parameters, added, or deleted.
 *
 * @author vulence
 */
public interface ExerciseServiceApi {

    /**
     * Retrieves all the exercises.
     *
     * @return A list with all the exercises, or an empty list if there are no exercises
     */
    List<Exercise> findAll();

    /**
     * Retrieves an exercise with the supplied ID.
     *
     * @param id The ID of the particular exercise
     *
     * @return An exercise with the supplied ID, or null if it doesn't exist
     */
    Exercise findById(Integer id);

    /**
     * Adds a new exercise.
     *
     * The new exercise should not be null.
     *
     * @param exercise The new exercise which is being added
     */
    void create(Exercise exercise);

    /**
     * Deletes an exercise.
     *
     * The ID of the exercise should not be null.
     *
     * @param id Id of the exercise that is being deleted
     */
    void delete(Integer id);

    /**
     * Retrieves all exercises that affect a particular muscle group.
     *
     * The muscle group with the muscleGroupName should not be null and should exist.
     *
     * @param muscleGroupName The name of the muscle group for which exercises should be retrieved
     *
     * @return A list of all exercises that affect the supplied muscle group, or an empty list if such exercises don't exist
     */
    List<Exercise> findAllExercisesByMuscleGroup(String muscleGroupName);
}
