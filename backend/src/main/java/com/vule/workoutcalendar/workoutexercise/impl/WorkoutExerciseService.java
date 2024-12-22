package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseServiceApi;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WorkoutExerciseService implements WorkoutExerciseServiceApi {

    /**
     * An exercise repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final ExerciseRepository exercises;

    /**
     * A WorkoutExercise joint table repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final WorkoutExerciseRepository workoutExercises;

    public WorkoutExerciseService(ExerciseRepository exercises, WorkoutExerciseRepository workoutExercises) {
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
    }

    @Override
    public List<WorkoutExercise> getWorkoutExercises(Integer workoutId) {
        List<WorkoutExercise> allExercises = workoutExercises.findAllByWorkoutId(workoutId).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        return allExercises;
    }

    @Override
    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        workoutExercises.save(workoutExercise);
    }

    @Override
    public void deleteWorkoutExercise(Integer WorkoutExerciseId) {
        WorkoutExercise we = workoutExercises.findById(WorkoutExerciseId).get();
        workoutExercises.delete(we);
    }
}