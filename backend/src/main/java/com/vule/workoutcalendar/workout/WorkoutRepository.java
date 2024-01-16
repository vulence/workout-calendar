package com.vule.workoutcalendar.workout;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends ListCrudRepository<Workout, Integer> {

    List<Workout> findByUserId(Integer userId);

    @Query("""
            SELECT *
            FROM WORKOUT
            WHERE user_id = :userId
            ORDER BY DATE desc
            LIMIT :limit
            OFFSET :offset
            """)
    List<Workout> findByUserIdPaged(@Param("userId") Integer userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    @Query("""
            SELECT *
            FROM WORKOUT
            WHERE id = :workoutId AND user_id = :userId
            """)
    Optional<Workout> findByIdAndUserId(@Param("workoutId") Integer workoutId, @Param("userId") Integer userId);

    @Query("""
            SELECT *
            FROM WORKOUT
            WHERE user_id = :userId AND date = :today
            """)
    Optional<Workout> findTodaysWorkout(@Param("userId") Integer userId, @Param("today") LocalDate today);

    @Modifying
    @Query("""
            DELETE
            FROM WORKOUT
            WHERE id = :workoutId AND user_id = :userId
            """)
    void deleteByIdAndUserId(@Param("workoutId") Integer workoutId, @Param("userId") Integer userId);

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
