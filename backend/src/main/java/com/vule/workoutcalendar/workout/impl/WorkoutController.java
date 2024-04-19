package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.api.WorkoutControllerApi;
import com.vule.workoutcalendar.workout.api.WorkoutServiceApi;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class WorkoutController implements WorkoutControllerApi {
    private final WorkoutServiceApi workoutServiceApi;

    private final JwtServiceApi jwtServiceApi;

    private static final Integer DEFAULT_PAGE_SIZE = 12;

    public WorkoutController(WorkoutServiceApi workoutServiceApi, JwtServiceApi jwtServiceApi) {
        this.workoutServiceApi = workoutServiceApi;
        this.jwtServiceApi = jwtServiceApi;
    }

    @Override
    @GetMapping("")
    @RequiresJwtToken
    public ResponseEntity<List<Workout>> findAllPaged(@RequestAttribute(name = "jwtToken") String jwtToken,
                                                      @RequestParam(required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(required = false, defaultValue = "DESC") String direction) {
        return ResponseEntity.ok(workoutServiceApi.findAllPaged(jwtServiceApi.parseUserIdFromJwt(jwtToken), page, DEFAULT_PAGE_SIZE, direction));
    }

    @Override
    @GetMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Workout> findById(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutServiceApi.findById(id, jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }

    @Override
    @GetMapping("/today")
    @RequiresJwtToken
    public ResponseEntity<Workout> findTodaysWorkout(@RequestAttribute(name = "jwtToken") String jwtToken) {
        return ResponseEntity.ok(workoutServiceApi.findTodaysWorkout(jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }

    @Override
    @GetMapping("/count")
    @RequiresJwtToken
    public ResponseEntity<Integer> getWorkoutCount(@RequestAttribute(name = "jwtToken") String jwtToken) {
        return ResponseEntity.ok(workoutServiceApi.getWorkoutCount(jwtServiceApi.parseUserIdFromJwt(jwtToken)));
    }

    @Override
    @PostMapping("")
    @RequiresJwtToken
    public ResponseEntity<Map<String, String>> create(@RequestAttribute(name = "jwtToken") String jwtToken, @Valid @RequestBody Workout workout) {
        workoutServiceApi.create(jwtServiceApi.parseUserIdFromJwt(jwtToken), workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout created successfully."));
    }

    @Override
    @PutMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Void> update(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody Workout workout) {
        workoutServiceApi.update(jwtServiceApi.parseUserIdFromJwt(jwtToken), id, workout);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<Void> delete(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        workoutServiceApi.delete(jwtServiceApi.parseUserIdFromJwt(jwtToken), id);
        return ResponseEntity.noContent().build();
    }
}