package com.vule.workoutcalendar.exercisemusclegroup.api;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

public interface ExerciseMuscleGroupServiceApi {

    ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(Exercise exercise);

    List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(Exercise exercise);

    List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(MuscleGroup muscleGroup);
}