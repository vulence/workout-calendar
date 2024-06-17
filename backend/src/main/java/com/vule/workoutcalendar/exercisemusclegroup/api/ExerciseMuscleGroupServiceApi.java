package com.vule.workoutcalendar.exercisemusclegroup.api;

import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;

import java.util.List;

public interface ExerciseMuscleGroupServiceApi {

    /**
     * Retrieves an ExerciseMuscleGroup that has its primary flag set to true,
     * as well as exerciseId equal to the Exercise object with the name of exerciseName.
     *
     * @param exerciseName The name of the exercise for which to find the primary muscle group
     * @return ExerciseMuscleGroup with primary flag for an exerciseName
     */
    ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName);

    /**
     * Retrieves a list of ExerciseMuscleGroup which have an exerciseId equal to the
     * Exercise object with the name of exerciseName.
     *
     * @param exerciseName The name of the exercise for which to find the associated muscle groups
     * @return A list of ExerciseMuscleGroup for the supplied exercise, or an empty list if they don't exist
     */
    List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(String exerciseName);

    /**
     * Retrieves a list of ExerciseMuscleGroup which have a muscleGroupId equal to the
     * MuscleGroup object with the name of muscleGroupName.
     *
     * @param muscleGroupName The name of the muscle group for which to find the associated exercises
     * @return A list of ExerciseMuscleGroup for the supplied muscle group, or an empty list if they don't exist
     */
    List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(String muscleGroupName);
}