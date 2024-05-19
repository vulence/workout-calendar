package com.vule.workoutcalendar.exercisemusclegroup.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.exercisemusclegroup.api.ExerciseMuscleGroupServiceApi;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.impl.MuscleGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseMuscleGroupService implements ExerciseMuscleGroupServiceApi {

    private final ExerciseRepository exerciseRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseMuscleGroupRepository exerciseMuscleGroupRepository;

    public ExerciseMuscleGroupService(ExerciseRepository exerciseRepository, MuscleGroupRepository muscleGroupRepository, ExerciseMuscleGroupRepository exerciseMuscleGroupRepository) {
        this.exerciseRepository = exerciseRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.exerciseMuscleGroupRepository = exerciseMuscleGroupRepository;
    }

    @Override
    public ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName) {
        Exercise exercise = exerciseRepository.findByName(exerciseName);
        return exerciseMuscleGroupRepository.findPrimaryMuscleGroupForExercise(exercise.getId());
    }

    @Override
    public List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(String exerciseName) {
        Exercise exercise = exerciseRepository.findByName(exerciseName);
        return exerciseMuscleGroupRepository.findAllMuscleGroupsForExercise(exercise.getId());
    }

    @Override
    public List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(String muscleGroupName) {
        MuscleGroup muscleGroup = muscleGroupRepository.findByName(muscleGroupName);
        return exerciseMuscleGroupRepository.findAllExercisesForMuscleGroup(muscleGroup.getId());
    }
}
