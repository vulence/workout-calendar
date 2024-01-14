package com.vule.workoutcalendar.workoutexercise;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkoutExerciseRepository extends ListCrudRepository<WorkoutExercise, Integer> {
    @Query("""
            SELECT *
            FROM WORKOUT_EXERCISE WE
            WHERE WE.WORKOUT_ID = :workoutId
            """)
    Optional<List<WorkoutExercise>> findWorkoutExercises(@Param("workoutId") Integer workoutId);
}
