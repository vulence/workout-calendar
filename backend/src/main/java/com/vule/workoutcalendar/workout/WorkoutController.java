package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
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

    public WorkoutController(WorkoutService workoutService, JwtService jwtService) {
        this.workoutService = workoutService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(@RequestHeader(name = "Authorization") String jwtToken) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        return ResponseEntity.ok(workoutService.findAll(jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        return ResponseEntity.ok(workoutService.findById(id, jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<?> getWorkoutExercises(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        return ResponseEntity.ok(workoutService.getWorkoutExercises(jwtService.parseUserIdFromJwt(jwtToken), id));
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestHeader(name = "Authorization") String jwtToken, @Valid @RequestBody Workout workout) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.create(jwtService.parseUserIdFromJwt(jwtToken), workout);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout created successfully."));
    }

    @PutMapping("/{id}/setNotes")
    public ResponseEntity<?> updateNotes(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @RequestBody String notes) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.updateNotes(jwtService.parseUserIdFromJwt(jwtToken), id, notes);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setDuration")
    public ResponseEntity<?> updateDuration(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @RequestBody Integer duration) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.updateDuration(jwtService.parseUserIdFromJwt(jwtToken), id, duration);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setRating")
    public ResponseEntity<?> updateRating(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @RequestBody Integer rating) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        jwtToken = jwtToken.substring(7);

        workoutService.updateRating(jwtService.parseUserIdFromJwt(jwtToken), id, rating);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setCompleted")
    public ResponseEntity<?> updateCompleted(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @RequestBody ObjectNode objectNode) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.updateCompleted(jwtService.parseUserIdFromJwt(jwtToken), id, objectNode.get("exerciseDoneId").asInt(), objectNode.get("completed").asBoolean());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.delete(jwtService.parseUserIdFromJwt(jwtToken), id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/new")
    public ResponseEntity<?> addWorkoutExercise(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.addWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDoneDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/update")
    public ResponseEntity<?> updateWorkoutExercise(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDone exerciseDone) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.updateWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDone);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/exercises")
    public ResponseEntity<?> deleteWorkoutExercise(@RequestHeader(name = "Authorization") String jwtToken, @PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        if (jwtToken == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        jwtToken = jwtToken.substring(7);

        workoutService.deleteWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDoneId);

        return ResponseEntity.noContent().build();
    }
}