package com.vule.workoutcalendar.musclegroup.api;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

public interface MuscleGroupServiceApi {

    List<MuscleGroup> findAll();

    MuscleGroup findById(Integer id);

    void create(MuscleGroup muscleGroup);

    void delete(Integer id);

    MuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName);

    List<MuscleGroup> findMuscleGroupsForExercise(String exerciseName);
}
