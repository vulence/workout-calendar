package com.vule.workoutcalendar.exercisemusclegroup.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.exercisemusclegroup.api.ExerciseMuscleGroupServiceApi;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.List;

public class ExerciseMuscleGroupService implements ExerciseMuscleGroupServiceApi {

    private final ExerciseMuscleGroupRepository exerciseMuscleGroupRepository;

    public ExerciseMuscleGroupService(ExerciseMuscleGroupRepository exerciseMuscleGroupRepository) {
        this.exerciseMuscleGroupRepository = exerciseMuscleGroupRepository;
    }

    @Override
    public ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(Exercise exercise) {
        return exerciseMuscleGroupRepository.findPrimaryMuscleGroupForExercise(exercise.getId());
    }

    @Override
    public List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(Exercise exercise) {
        return exerciseMuscleGroupRepository.findAllMuscleGroupsForExercise(exercise.getId());
    }

    @Override
    public List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseMuscleGroupRepository.findAllExercisesForMuscleGroup(muscleGroup.getId());
    }
}
