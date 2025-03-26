package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exerciseset.ExerciseSet;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import com.vule.workoutcalendar.workout.CompletedWorkout;
import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.api.WorkoutServiceApi;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/workouts")
public class WorkoutController {
    private final WorkoutServiceApi workoutServiceApi;

    private final JwtServiceApi jwtServiceApi;

    private static final Integer DEFAULT_PAGE_SIZE = 12;

    public WorkoutController(WorkoutServiceApi workoutServiceApi, JwtServiceApi jwtServiceApi) {
        this.workoutServiceApi = workoutServiceApi;
        this.jwtServiceApi = jwtServiceApi;
    }

    @GetMapping("")
    @RequiresJwtToken
    public ResponseEntity<List<Workout>> findAllPaged(@RequestAttribute String jwtToken,
                                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(workoutServiceApi.findAllPaged(jwtServiceApi.parseUserIdFromJwt(jwtToken), page, DEFAULT_PAGE_SIZE, direction));
    }

    @GetMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Workout> findById(@RequestAttribute String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutServiceApi.findById(id, jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/{id}/details")
    @RequiresJwtToken
    public ResponseEntity<Map<String, List<ExerciseSet>>> findWorkoutDetails(@RequestAttribute String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutServiceApi.findWorkoutDetails(jwtServiceApi.parseUserIdFromJwt(jwtToken), id));
    }

    @GetMapping("/count")
    @RequiresJwtToken
    public ResponseEntity<Integer> getWorkoutCount(@RequestAttribute String jwtToken) {
        return ResponseEntity.ok(workoutServiceApi.getWorkoutCount(jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }

    @PostMapping("")
    @RequiresJwtToken
    public ResponseEntity<Map<String, String>> create(@RequestAttribute String jwtToken, @Valid @RequestBody Workout workout) {
        workoutServiceApi.create(jwtServiceApi.parseUserIdFromJwt(jwtToken), workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout created successfully."));
    }

    @PutMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Void> update(@RequestAttribute String jwtToken, @PathVariable Integer id, @Valid @RequestBody Workout workout) {
        workoutServiceApi.update(jwtServiceApi.parseUserIdFromJwt(jwtToken), id, workout);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Void> delete(@RequestAttribute String jwtToken, @PathVariable Integer id) {
        workoutServiceApi.delete(jwtServiceApi.parseUserIdFromJwt(jwtToken), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    @RequiresJwtToken
    public ResponseEntity<Map<String, String>> createCompletedWorkout(@RequestAttribute String jwtToken, @PathVariable Integer id, @RequestBody CompletedWorkout completedWorkout) {
        workoutServiceApi.createCompletedWorkout(jwtServiceApi.parseUserIdFromJwt(jwtToken), completedWorkout);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout completed successfully."));
    }

    @GetMapping("/completed")
    @RequiresJwtToken
    public ResponseEntity<List<CompletedWorkout>> findAllCompletedWorkouts(@RequestAttribute String jwtToken) {
       return ResponseEntity.ok(workoutServiceApi.findAllCompletedWorkouts(jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }
}