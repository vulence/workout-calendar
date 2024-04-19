package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends ListCrudRepository<Exercise, Integer> {

    @Query("""
            SELECT E.*
            FROM EXERCISE E
            INNER JOIN EXERCISE_MUSCLE_GROUP EMG ON EMG.MUSCLE_GROUP = :muscleGroupId
            WHERE E.ID = EMG.EXERCISE
            """)
    List<Exercise> findByMuscleGroup(@Param("muscleGroupId") Integer muscleGroupId);

    @Query("""
            SELECT NAME
            FROM EXERCISE
            WHERE ID = :exerciseId
            """)
    String findExerciseName(@Param("exerciseId") Integer exerciseId);

    @Query("""
           SELECT W.DATE, WE.*
           FROM WORKOUT W
           INNER JOIN WORKOUT_EXERCISE WE ON W.ID = WE.WORKOUT_ID
           WHERE W.USER_ID = :userId AND WE.EXERCISE_ID = :exerciseId
           ORDER BY W.DATE ASC
            """)
    List<ExerciseHistoryDto> findExerciseHistory(@Param("userId") Integer userId, @Param("exerciseId") Integer id);

    @Query("""
          SELECT W.DATE, MAX(WE.WEIGHT) AS Weight
          FROM WORKOUT W
          INNER JOIN WORKOUT_EXERCISE WE ON W.ID = WE.WORKOUT_ID
          WHERE W.USER_ID = :userId AND WE.EXERCISE_ID = :exerciseId
          GROUP BY W.DATE
          ORDER BY W.DATE ASC
            """)
    List<ExerciseHistoryDto> findMaxWeights(@Param("userId") Integer userId, @Param("exerciseId") Integer id);
}