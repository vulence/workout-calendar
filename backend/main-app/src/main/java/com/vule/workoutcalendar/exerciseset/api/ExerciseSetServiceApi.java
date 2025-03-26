package com.vule.workoutcalendar.exerciseset.api;

import com.vule.workoutcalendar.exerciseset.ExerciseSet;

import java.util.List;

public interface ExerciseSetServiceApi {
    List<ExerciseSet> getExerciseSetsByWorkoutExerciseId(Integer workoutExerciseId);
}
