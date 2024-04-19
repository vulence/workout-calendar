package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.workout.Workout;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends ListCrudRepository<Workout, Integer> {
    List<Workout> findByUserId(Integer userId, PageRequest pageRequest);

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

    @Query("""
            SELECT COUNT(*)
            FROM WORKOUT
            WHERE user_id = :userId
            """)
    Integer getWorkoutCount(@Param("userId") Integer userId);

    @Modifying
    @Query("""
            DELETE
            FROM WORKOUT
            WHERE id = :workoutId AND user_id = :userId
            """)
    void deleteByIdAndUserId(@Param("workoutId") Integer workoutId, @Param("userId") Integer userId);
}