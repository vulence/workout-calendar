package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends ListCrudRepository<Exercise, Integer> {
    @Query("""
            SELECT NAME
            FROM EXERCISE
            WHERE ID = :exerciseId
            """)
    String findExerciseName(@Param("exerciseId") Integer exerciseId);

    @Query("""
        SELECT *
        FROM EXERCISE
        WHERE lower(name) = lower(:name)
        """)
    Exercise findByName(String name);
}