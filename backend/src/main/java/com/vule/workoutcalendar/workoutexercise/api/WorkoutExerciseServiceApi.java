package com.vule.workoutcalendar.workoutexercise.api;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;

import java.util.List;

public interface WorkoutExerciseServiceApi {

    List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer workoutId);

    List<GroupedExerciseDto> getGroupedWorkoutExercises(Integer userId, Integer workoutId);

    void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto);

    void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise);

    void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId);
}
