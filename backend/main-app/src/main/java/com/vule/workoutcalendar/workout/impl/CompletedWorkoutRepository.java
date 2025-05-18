package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.workout.CompletedWorkout;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface CompletedWorkoutRepository extends ListCrudRepository<CompletedWorkout, Integer> {

    @Query("""
            SELECT cw.*
            FROM COMPLETED_WORKOUT cw
            INNER JOIN WORKOUT w ON cw.workout_id = w.id AND w.user_id = :userId
            """)
    List<CompletedWorkout> findAllByUserId(Integer userId);
}
