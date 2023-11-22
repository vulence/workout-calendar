package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.workout.dto.WorkoutDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
    public Workout findById(@PathVariable Integer id) {
        return workoutService.findById(id);
    }

    @GetMapping("/{id}/exercises")
    public List<Exercise> getWorkoutDetails(@PathVariable Integer id) {
        return workoutService.getWorkoutDetails(id);
    }

    @GetMapping("/{id}/muscleGroups")
    public Set<String> getWorkoutMuscleGroups(@PathVariable Integer id) {
        return workoutService.getWorkoutMuscleGroups(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void create(Authentication authentication, @Valid @RequestBody Workout workout) {
        workoutService.create(authentication.getName(), workout);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setNotes")
    public void updateNotes(@PathVariable Integer id, @RequestBody String notes) {
        workoutService.updateNotes(id, notes);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setDuration")
    public void updateDuration(@PathVariable Integer id, @RequestBody Integer duration) {
        workoutService.updateDuration(id, duration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setRating")
    public void updateRating(@PathVariable Integer id, @RequestBody Integer rating) {
        workoutService.updateRating(id, rating);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        workoutService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/exercises/new")
    public void addWorkoutExercise(@PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        workoutService.addWorkoutExercise(id, exerciseDoneDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/exercises/update")
    public void updateWorkoutExercise(@PathVariable Integer id, @Valid @RequestBody DataGridExerciseDto dataGridExerciseDto) {
        workoutService.updateWorkoutExercise(id, dataGridExerciseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/exercises")
    public void deleteWorkoutExercise(@PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        workoutService.deleteWorkoutExercise(id, exerciseDoneId);
    }
}