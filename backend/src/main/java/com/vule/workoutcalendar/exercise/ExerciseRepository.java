package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExerciseRepository extends ListCrudRepository<Exercise, Integer> {
    @Query("""
           SELECT W.DATE, ED.*
           FROM WORKOUT W
           INNER JOIN EXERCISE_DONE ED ON W.ID = ED.WORKOUT
           WHERE W.USER_ID = :userId AND ED.EXERCISE = :exerciseId
           ORDER BY W.DATE ASC
            """)
    List<ExerciseHistoryDto> findExerciseHistory(@Param("userId") Integer userId, @Param("exerciseId") Integer id);

    @Query("""
          SELECT W.DATE, MAX(ED.WEIGHT) AS Weight
          FROM WORKOUT W
          INNER JOIN EXERCISE_DONE ED ON W.ID = ED.WORKOUT
          WHERE W.USER_ID = :userId AND ED.EXERCISE = :exerciseId
          GROUP BY W.DATE
          ORDER BY W.DATE ASC
            """)
    List<ExerciseHistoryDto> findMaxWeights(@Param("userId") Integer userId, @Param("exerciseId") Integer id);
}