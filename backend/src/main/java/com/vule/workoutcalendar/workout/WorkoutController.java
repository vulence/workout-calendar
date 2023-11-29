package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> findAll(@CookieValue(name = "jwt", required = false) String jwtToken) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - HttpOnly cookie missing or invalid.");

        return ResponseEntity.ok(workoutService.findAll(jwtService.parseUsernameFromJwt(jwtToken)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.findById(jwtService.parseUsernameFromJwt(jwtToken), id));
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<?> getWorkoutDetails(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.getWorkoutDetails(jwtService.parseUsernameFromJwt(jwtToken), id));
    }

    @GetMapping("/{id}/muscleGroups")
    public ResponseEntity<?> getWorkoutMuscleGroups(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(workoutService.getWorkoutMuscleGroups(jwtService.parseUsernameFromJwt(jwtToken), id));
    }


    @PostMapping("/new")
    public ResponseEntity<?> create(@CookieValue(name = "jwt", required = false) String jwtToken, @Valid @RequestBody Workout workout) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.create(jwtService.parseUsernameFromJwt(jwtToken), workout);

        return ResponseEntity.status(HttpStatus.CREATED).body("Workout created successfully.");
    }

    @PutMapping("/{id}/setNotes")
    public ResponseEntity<?> updateNotes(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody String notes) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateNotes(jwtService.parseUsernameFromJwt(jwtToken), id, notes);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout notes updated successfully.");
    }

    @PutMapping("/{id}/setDuration")
    public ResponseEntity<?> updateDuration(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer duration) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateDuration(jwtService.parseUsernameFromJwt(jwtToken), id, duration);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout duration updated successfully.");
    }

    @PutMapping("/{id}/setRating")
    public ResponseEntity<?> updateRating(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer rating) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateRating(jwtService.parseUsernameFromJwt(jwtToken), id, rating);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout rating updated successfully.");
    }

    @PatchMapping("/{id}/setFinished")
    public ResponseEntity<?> updateFinished(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateFinished(jwtService.parseUsernameFromJwt(jwtToken), id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout finished status updated successfully.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.delete(jwtService.parseUsernameFromJwt(jwtToken), id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout deleted successfully.");
    }

    @PutMapping("/{id}/exercises/new")
    public ResponseEntity<?> addWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.addWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, exerciseDoneDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exercise added successfully.");
    }

    @PutMapping("/{id}/exercises/update")
    public ResponseEntity<?> updateWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @Valid @RequestBody DataGridExerciseDto dataGridExerciseDto) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, dataGridExerciseDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Workout exercise updated successfully.");
    }

    @DeleteMapping("/{id}/exercises")
    public ResponseEntity<?> deleteWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.deleteWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, exerciseDoneId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Exercise deleted successfully.");
    }
}