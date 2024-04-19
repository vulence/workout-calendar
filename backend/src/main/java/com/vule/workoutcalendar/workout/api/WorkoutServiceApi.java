package com.vule.workoutcalendar.workout.api;

import com.vule.workoutcalendar.workout.Workout;

import java.util.List;

public interface WorkoutServiceApi {

    List<Workout> findAllPaged(Integer userId, Integer page, Integer size, String direction);

    Workout findById(Integer id, Integer userId);

    Workout findTodaysWorkout(Integer userId);

    Integer getWorkoutCount(Integer userId);

    void create(Integer userId, Workout workout);

    void update(Integer userId, Integer workoutId, Workout workout);

    void delete(Integer userId, Integer id);
}
