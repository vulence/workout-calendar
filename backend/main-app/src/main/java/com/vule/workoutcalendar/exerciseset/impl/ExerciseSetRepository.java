package com.vule.workoutcalendar.exerciseset.impl;

import com.vule.workoutcalendar.exerciseset.ExerciseSet;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseSetRepository extends ListCrudRepository<ExerciseSet, Integer> {
    @Query("""
            SELECT *
            FROM EXERCISE_SET
            WHERE WORKOUT_EXERCISE_ID = :workoutExerciseId
            """)
    Optional<List<ExerciseSet>> findAllByWorkoutExerciseId(Integer workoutExerciseId);
}
