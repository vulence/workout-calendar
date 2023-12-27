package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
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
    @RequiresJwtToken
    public ResponseEntity<?> findAll(@RequestAttribute(name = "jwtToken") String jwtToken) {
        return ResponseEntity.ok(workoutService.findAll(jwtService.parseUserIdFromJwt(jwtToken)));
    }

    @GetMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<?> findById(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutService.findById(id, jwtService.parseUserIdFromJwt(jwtToken)));
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

    @PutMapping("/{id}/setNotes")
    @RequiresJwtToken
    public ResponseEntity<?> updateNotes(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody String notes) {
        workoutService.updateNotes(jwtService.parseUserIdFromJwt(jwtToken), id, notes);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setDuration")
    @RequiresJwtToken
    public ResponseEntity<?> updateDuration(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody Integer duration) {
        workoutService.updateDuration(jwtService.parseUserIdFromJwt(jwtToken), id, duration);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setRating")
    @RequiresJwtToken
    public ResponseEntity<?> updateRating(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody Integer rating) {
        workoutService.updateRating(jwtService.parseUserIdFromJwt(jwtToken), id, rating);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setCompleted")
    @RequiresJwtToken
    public ResponseEntity<?> updateCompleted(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody ObjectNode objectNode) {
        workoutService.updateCompleted(jwtService.parseUserIdFromJwt(jwtToken), id, objectNode.get("exerciseDoneId").asInt(), objectNode.get("completed").asBoolean());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public ResponseEntity<?> delete(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        workoutService.delete(jwtService.parseUserIdFromJwt(jwtToken), id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/new")
    @RequiresJwtToken
    public ResponseEntity<?> addWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        workoutService.addWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDoneDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/update")
    @RequiresJwtToken
    public ResponseEntity<?> updateWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDone exerciseDone) {
        workoutService.updateWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDone);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/exercises")
    @RequiresJwtToken
    public ResponseEntity<?> deleteWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        workoutService.deleteWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, exerciseDoneId);
        return ResponseEntity.noContent().build();
    }
}