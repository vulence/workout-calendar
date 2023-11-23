package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(Authentication authentication) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.findAll(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.findById(authentication.getName(), id));
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<?> getWorkoutDetails(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.getWorkoutDetails(authentication.getName(), id));
    }

    @GetMapping("/{id}/muscleGroups")
    public ResponseEntity<?> getWorkoutMuscleGroups(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.getWorkoutMuscleGroups(authentication.getName(), id));
    }


    @PostMapping("/new")
    public ResponseEntity<?> create(Authentication authentication, @Valid @RequestBody Workout workout) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.create(authentication.getName(), workout);

        return ResponseEntity.status(HttpStatus.CREATED).body("Workout created successfully.");
    }

    @PutMapping("/{id}/setNotes")
    public ResponseEntity<?> updateNotes(Authentication authentication, @PathVariable Integer id, @RequestBody String notes) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateNotes(authentication.getName(), id, notes);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout notes updated successfully.");
    }

    @PutMapping("/{id}/setDuration")
    public ResponseEntity<?> updateDuration(Authentication authentication, @PathVariable Integer id, @RequestBody Integer duration) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateDuration(authentication.getName(), id, duration);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout duration updated successfully.");
    }

    @PutMapping("/{id}/setRating")
    public ResponseEntity<?> updateRating(Authentication authentication, @PathVariable Integer id, @RequestBody Integer rating) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateRating(authentication.getName(), id, rating);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout rating updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.delete(authentication.getName(), id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout deleted successfully.");
    }

    @PutMapping("/{id}/exercises/new")
    public ResponseEntity<?> addWorkoutExercise(Authentication authentication, @PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.addWorkoutExercise(authentication.getName(), id, exerciseDoneDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exercise added successfully.");
    }

    @PutMapping("/{id}/exercises/update")
    public ResponseEntity<?> updateWorkoutExercise(Authentication authentication, @PathVariable Integer id, @Valid @RequestBody DataGridExerciseDto dataGridExerciseDto) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateWorkoutExercise(authentication.getName(), id, dataGridExerciseDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout exercise updated successfully.");
    }

    @DeleteMapping("/{id}/exercises")
    public ResponseEntity<?> deleteWorkoutExercise(Authentication authentication, @PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.deleteWorkoutExercise(authentication.getName(), id, exerciseDoneId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exercise deleted successfully.");
    }
}