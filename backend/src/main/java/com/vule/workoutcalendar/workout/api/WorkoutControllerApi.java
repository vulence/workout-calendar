package com.vule.workoutcalendar.workout.api;

import com.vule.workoutcalendar.workout.Workout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/workouts")
public interface WorkoutControllerApi {

    ResponseEntity<List<Workout>> findAllPaged(String jwtToken, Integer page, String direction);

    ResponseEntity<Workout> findById(String jwtToken, Integer id);

    ResponseEntity<Workout> findTodaysWorkout(String jwtToken);

    ResponseEntity<Integer> getWorkoutCount(String jwtToken);

    ResponseEntity<Map<String, String>> create(String jwtToken, Workout workout);

    ResponseEntity<Void> update(String jwtToken, Integer id, Workout workout);

    ResponseEntity<Void> delete(String jwtToken, Integer id);
}
