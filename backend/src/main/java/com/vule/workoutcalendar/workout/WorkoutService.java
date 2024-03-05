package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.WorkoutExerciseRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
class WorkoutService {
    private final WorkoutRepository workouts;

    WorkoutService(WorkoutRepository workouts) {
        this.workouts = workouts;
    }

    List<Workout> findAll(Integer userId, int page, int size, String direction) {
        if (page <= 0) return workouts.findByUserId(userId, PageRequest.of(0, Integer.MAX_VALUE));
        else return workouts.findByUserId(userId, PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(direction), "DATE")));
    }

    Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    Workout findTodaysWorkout(Integer userId) {
        return workouts.findTodaysWorkout(userId, LocalDate.now()).orElse(null);
    }

    int getWorkoutCount(Integer userId) {
        return workouts.getWorkoutCount(userId);
    }

    void create(Integer userId, Workout workout) {
        workout.setUserId(userId);

        workouts.save(workout);
    }

    void update(Integer userId, Integer workoutId, Workout w) {
        Workout dbWorkout = findById(workoutId, userId);
        dbWorkout.setDuration(w.getDuration());
        dbWorkout.setNotes(w.getNotes());
        dbWorkout.setRating(w.getRating());

        workouts.save(dbWorkout);
    }

    void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }
}
