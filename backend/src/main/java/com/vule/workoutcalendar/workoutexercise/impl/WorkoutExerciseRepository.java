package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseRepository extends ListCrudRepository<WorkoutExercise, Integer> {
    @Query("""
            SELECT *
            FROM WORKOUT_EXERCISE
            WHERE WORKOUT_ID = :workoutId
            """)
    Optional<List<WorkoutExercise>> findAllByWorkoutId(@Param("workoutId") Integer workoutId);
}
