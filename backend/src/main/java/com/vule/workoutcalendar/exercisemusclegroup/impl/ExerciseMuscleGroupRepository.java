package com.vule.workoutcalendar.exercisemusclegroup.impl;

import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ExerciseMuscleGroupRepository extends ListCrudRepository<ExerciseMuscleGroup, Integer> {

    @Query("""
        SELECT *
        FROM EXERCISE_MUSCLE_GROUP
        WHERE EXERCISE_ID = :exerciseId and PRIMARY = true
        """)
    ExerciseMuscleGroup findPrimaryMuscleGroupForExercise(Integer exerciseId);

    @Query("""
       SELECT *
       FROM EXERCISE_MUSCLE_GROUP
       WHERE EXERCISE_ID = :exerciseId
       """)
    List<ExerciseMuscleGroup> findAllMuscleGroupsForExercise(Integer exerciseId);

    @Query("""
        SELECT *
        FROM EXERCISE_MUSCLE_GROUP
        WHERE MUSCLE_GROUP_ID = :muscleGroupId
        """)
    List<ExerciseMuscleGroup> findAllExercisesForMuscleGroup(Integer muscleGroupId);
}
