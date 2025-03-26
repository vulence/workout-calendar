package com.vule.workoutcalendar.exerciseset.impl;

import com.vule.workoutcalendar.exerciseset.ExerciseSet;
import com.vule.workoutcalendar.exerciseset.api.ExerciseSetServiceApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseSetService implements ExerciseSetServiceApi {

    private final ExerciseSetRepository repository;

    public ExerciseSetService(ExerciseSetRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ExerciseSet> getExerciseSetsByWorkoutExerciseId(Integer workoutExerciseId) {
        return repository.findAllByWorkoutExerciseId(workoutExerciseId).orElse(null);
    }
}
