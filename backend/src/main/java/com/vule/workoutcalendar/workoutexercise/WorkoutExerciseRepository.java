package com.vule.workoutcalendar.workoutexercise;

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
    Optional<List<WorkoutExercise>> findWorkoutExercises(@Param("workoutId") Integer workoutId);

    @Query("""
           SELECT *
           FROM WORKOUT_EXERCISE
           WHERE ID = :id
            """)
    WorkoutExercise findWorkoutExercise(@Param("id") Integer id);

    @Modifying
    @Query("""
            UPDATE WORKOUT_EXERCISE
            SET WEIGHT = :weight, SETS = :sets, REPS = :reps, COMPLETED = :completed
            WHERE ID = :id
            """)
    void updateWorkoutExercise(@Param("id") Integer id, @Param("weight") Integer weight, @Param("sets") Integer sets, @Param("reps") Integer reps, @Param("completed") Boolean completed);

    @Modifying
    @Query("""
          DELETE
          FROM WORKOUT_EXERCISE
          WHERE ID = :id
            """)
    void deleteWorkoutExercise(@Param("id") Integer id);
}
