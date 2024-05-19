package com.vule.workoutcalendar.exercisemusclegroup.api;

import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;

import java.util.List;

public interface ExerciseMuscleGroupServiceApi {

    ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName);

    List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(String exerciseName);

    List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(String muscleGroupName);
}