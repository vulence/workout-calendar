package com.vule.workoutcalendar.exercise.api;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

public interface ExerciseServiceApi {

    List<Exercise> findAll();

    Exercise findById(Integer id);

    void create(Exercise exercise);

    void delete(Integer id);

    List<Exercise> findAllExercisesByMuscleGroup(String muscleGroupName);
}
