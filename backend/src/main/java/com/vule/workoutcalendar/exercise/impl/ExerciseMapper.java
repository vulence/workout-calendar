package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseMapper {

    private final ExerciseRepository exerciseRepository;

    public ExerciseMapper(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> mapToExercises(List<ExerciseMuscleGroup> exerciseMuscleGroupList) {
        return exerciseMuscleGroupList.stream().map(
                emg -> exerciseRepository.findById(emg.getExerciseId())
                        .orElseThrow()
        ).toList();
    }
}
