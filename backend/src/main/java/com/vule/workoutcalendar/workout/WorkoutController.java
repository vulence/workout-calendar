package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;
    private final JwtService jwtService;

    private static final int DEFAULT_PAGE_SIZE = 12;

    public WorkoutController(WorkoutService workoutService, JwtService jwtService) {
        this.workoutService = workoutService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    @RequiresJwtToken
    public ResponseEntity<?> findAll(@RequestAttribute(name = "jwtToken") String jwtToken, @RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(workoutService.findAll(jwtService.parseUserIdFromJwt(jwtToken), page, DEFAULT_PAGE_SIZE));
    }

    @GetMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<?> findById(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutService.findById(id, jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/today")
    @RequiresJwtToken
    public ResponseEntity<?> findTodaysWorkout(@RequestAttribute(name = "jwtToken") String jwtToken) {
        return ResponseEntity.ok(workoutService.findTodaysWorkout(jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/count")
    @RequiresJwtToken
    public ResponseEntity<?> getWorkoutCount(@RequestAttribute(name = "jwtToken") String jwtToken) {
        return ResponseEntity.ok(workoutService.getWorkoutCount(jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/{id}/exercises")
    @RequiresJwtToken
    public ResponseEntity<?> getWorkoutExercises(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutService.getWorkoutExercises(jwtService.parseUserIdFromJwt(jwtToken), id));
    }

    @PostMapping("/new")
    @RequiresJwtToken
    public ResponseEntity<?> create(@RequestAttribute(name = "jwtToken") String jwtToken, @Valid @RequestBody Workout workout) {
        workoutService.create(jwtService.parseUserIdFromJwt(jwtToken), workout);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout created successfully."));
    }

    @PostMapping("/{id}/exercises")
    @RequiresJwtToken
    public ResponseEntity<?> addWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody WorkoutExerciseDto workoutExerciseDto) {
        workoutService.addWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExerciseDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/update")
    @RequiresJwtToken
    public ResponseEntity<?> updateWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody WorkoutExercise workoutExercise) {
        workoutService.updateWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExercise);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/update")
    @RequiresJwtToken
    public ResponseEntity<?> update(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @NotNull @RequestBody ObjectNode objectNode) {
        workoutService.update(jwtService.parseUserIdFromJwt(jwtToken), id, objectNode);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setCompleted")
    @RequiresJwtToken
    public ResponseEntity<?> updateWorkoutExerciseCompleted(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @NotNull @RequestBody ObjectNode objectNode) {
        workoutService.updateWorkoutExerciseCompleted(jwtService.parseUserIdFromJwt(jwtToken), id, objectNode.get("workoutExerciseId").asInt(), objectNode.get("completed").asBoolean());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<?> delete(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        workoutService.delete(jwtService.parseUserIdFromJwt(jwtToken), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/exercises")
    @RequiresJwtToken
    public ResponseEntity<?> deleteWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody Integer workoutExerciseId) {
        workoutService.deleteWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}