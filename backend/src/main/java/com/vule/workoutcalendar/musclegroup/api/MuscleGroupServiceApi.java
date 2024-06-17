package com.vule.workoutcalendar.musclegroup.api;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

/**
 * Represents an API for the MuscleGroup service.
 *
 * Muscle groups can be retrieved according to different parameters, added, or deleted.
 *
 * @author vulence
 */
public interface MuscleGroupServiceApi {

    /**
     * Retrieves all the muscle groups.
     *
     * @return A list with all the muscle groups, or an empty list if there are no exercises
     */
    List<MuscleGroup> findAll();

    /**
     * Retrieves a muscle group with the supplied ID.
     *
     * @param id The ID of the particular muscle group
     *
     * @return A muscle group with the supplied ID, or null if it doesn't exist
     */
    MuscleGroup findById(Integer id);

    /**
     * Adds a new muscle group.
     *
     * The new muscle group should not be null.
     *
     * @param muscleGroup The new muscle group which is being added
     */
    void create(MuscleGroup muscleGroup);

    /**
     * Deletes a muscle group.
     *
     * The ID of the muscle group should not be null.
     *
     * @param id Id of the muscle group that is being deleted
     */
    void delete(Integer id);

    /**
     * Retrieves a muscle group that is marked as primary for the particular exercise.
     *
     * The exercise with the exerciseName should not be null and should exist.
     *
     * @param exerciseName The name of the exercise for which the primary muscle group should be retrieved
     *
     * @return A muscle group that is marked as primary for the supplied exercise
     */
    MuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName);

    /**
     * Retrieves all muscle groups that are affected by a particular exercise.
     *
     * The exercise with the exerciseName should not be null and should exist.
     *
     * @param exerciseName The name of the exercise for which muscle groups should be retrieved
     *
     * @return A list of all muscle groups that are affected by the supplied exercise, or an empty list if such muscle groups don't exist
     */
    List<MuscleGroup> findMuscleGroupsForExercise(String exerciseName);
}
