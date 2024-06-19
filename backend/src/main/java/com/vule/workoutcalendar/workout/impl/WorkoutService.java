package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.api.WorkoutServiceApi;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService implements WorkoutServiceApi {

    /**
     * A workout repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final WorkoutRepository workouts;

    public WorkoutService(WorkoutRepository workouts) {
        this.workouts = workouts;
    }

    @Override
    public List<Workout> findAllPaged(Integer userId, Integer page, Integer size, String direction) {
        if (page <= 0) return workouts.findByUserId(userId, PageRequest.of(0, Integer.MAX_VALUE, Sort.by(Sort.Direction.fromString(direction), "DATE")));
        else return workouts.findByUserId(userId, PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(direction), "DATE")));
    }

    @Override
    public Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    @Override
    public Workout findTodaysWorkout(Integer userId) {
        return workouts.findTodaysWorkout(userId, LocalDate.now()).orElse(null);
    }

    @Override
    public Integer getWorkoutCount(Integer userId) {
        return workouts.getWorkoutCount(userId);
    }

    @Override
    public void create(Integer userId, Workout workout) {
        workout.setUserId(userId);

        workouts.save(workout);
    }

    @Override
    public void update(Integer userId, Integer workoutId, Workout workout) {
        Workout dbWorkout = findById(workoutId, userId);
        dbWorkout.setDuration(workout.getDuration());
        dbWorkout.setNotes(workout.getNotes());
        dbWorkout.setRating(workout.getRating());

        workouts.save(dbWorkout);
    }

    @Override
    public void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }
}
