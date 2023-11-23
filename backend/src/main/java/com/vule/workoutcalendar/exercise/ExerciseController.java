package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    public List<ExerciseDto> findAll() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Integer id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/{id}/exerciseHistory")
    public ResponseEntity<?> findExerciseHistory(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(exerciseService.findExerciseHistory(authentication.getName(), id));
    }
    @GetMapping("/{id}/workouts")
    public ResponseEntity<?> findMaxWeights(Authentication authentication, @PathVariable Integer id) {
        if (authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        return ResponseEntity.ok(exerciseService.findMaxWeights(authentication.getName(), id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.create(exercise);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        exerciseService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/muscleGroups/new")
    public void addMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.addMuscleGroup(id, muscleGroupId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/muscleGroups")
    public void deleteMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.deleteMuscleGroup(id, muscleGroupId);
    }
}
