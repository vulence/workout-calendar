package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends ListCrudRepository<Workout, Integer> {
    List<Workout> findByUserId(Integer userId);

    @Query("""
            SELECT *
            FROM WORKOUT
            WHERE id = :workoutId AND user_id = :userId
            """)
    Optional<Workout> findByIdAndUserId(@Param("workoutId") Integer workoutId, @Param("userId") Integer userId);

    @Query("""
            DELETE
            FROM WORKOUT
            WHERE id = :workoutId AND user_id = :userId
            """)
    void deleteByIdAndUserId(@Param("workoutId") Integer workoutId, @Param("userId") Integer userId);

    @Query("""
           SELECT *
           FROM EXERCISE_DONE
           WHERE ID = :id
            """)
    ExerciseDone findExerciseDone(@Param("id") Integer id);

    @Modifying
    @Query("""
            UPDATE EXERCISE_DONE
            SET WEIGHT = :weight, SETS = :sets, REPS = :reps
            WHERE ID = :id
            """)
    void updateExerciseDone(@Param("id") Integer id, @Param("weight") Integer weight, @Param("sets") Integer sets, @Param("reps") Integer reps);

    @Modifying
    @Query("""
          DELETE
          FROM EXERCISE_DONE
          WHERE ID = :id
            """)
    void deleteExerciseDone(@Param("id") Integer id);

    @Modifying
    @Query("""
            UPDATE WORKOUT
            SET NOTES = :notes
            WHERE ID = :id
            """)
    void updateNotes(@Param("id") Integer id, @Param("notes") String notes);

    @Modifying
    @Query("""
            UPDATE WORKOUT
            SET DURATION = :duration
            WHERE ID = :id
            """)
    void updateDuration(@Param("id") Integer id, @Param("duration") Integer duration);

    @Modifying
    @Query("""
           UPDATE WORKOUT
           SET RATING = :rating
           WHERE ID = :id
            """)
    void updateRating(@Param("id") Integer id, @Param("rating") Integer rating);
}
