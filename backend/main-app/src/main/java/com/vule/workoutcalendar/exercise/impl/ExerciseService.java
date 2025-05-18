package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.api.ExerciseServiceApi;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService implements ExerciseServiceApi {

    /**
     * An exercise repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final ExerciseRepository repository;

    public ExerciseService(ExerciseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Exercise> findAll() {
        return repository.findAll();
    }

    @Override
    public Exercise findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void create(Exercise exercise) {
        if (repository.findAll().stream().noneMatch(e -> e.getName().toLowerCase().trim().equals(exercise.getName().toLowerCase().trim()))) {
            repository.save(exercise);
        }
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
