package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.api.ExerciseServiceApi;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import com.vule.workoutcalendar.exercisemusclegroup.api.ExerciseMuscleGroupServiceApi;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService implements ExerciseServiceApi {

    /**
     * An exercise repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final ExerciseRepository exercises;

    /**
     * An API that communicates with the Exercise-MuscleGroup service.
     */
    private final ExerciseMuscleGroupServiceApi exerciseMuscleGroupServiceApi;

    /**
     * A mapper that maps ExerciseMuscleGroup objects to an Exercise object.
     */
    private final ExerciseMapper exerciseMapper;

    public ExerciseService(ExerciseRepository exercises, ExerciseMuscleGroupServiceApi exerciseMuscleGroupServiceApi, ExerciseMapper exerciseMapper) {
        this.exercises = exercises;
        this.exerciseMuscleGroupServiceApi = exerciseMuscleGroupServiceApi;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public List<Exercise> findAll() {
        return exercises.findAll();
    }

    @Override
    public Exercise findById(Integer id) {
        return exercises.findById(id).orElse(null);
    }

    public List<ExerciseHistoryDto> findExerciseHistory(Integer userId, Integer id) {
        return exercises.findExerciseHistory(userId, id);
    }

    public List<ExerciseHistoryDto> findMaxWeights(Integer userId, Integer id) {
        return exercises.findMaxWeights(userId, id);
    }

    @Override
    public void create(Exercise exercise) {
        if (exercises.findAll().stream().noneMatch(e -> e.getName().toLowerCase().trim().equals(exercise.getName().toLowerCase().trim()))) {
            exercises.save(exercise);
        }
    }

    @Override
    public void delete(Integer id) {
        exercises.deleteById(id);
    }

    @Override
    public List<Exercise> findAllExercisesByMuscleGroup(String muscleGroupName) {
        return exerciseMapper.mapToExercises(exerciseMuscleGroupServiceApi.findAllExercisesForMuscleGroup(muscleGroupName));
    }
}
