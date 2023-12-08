package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final JwtService jwtService;

    public ExerciseController(ExerciseService exerciseService, JwtService jwtService) {
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
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
    public ResponseEntity<?> findExerciseHistory(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        return ResponseEntity.ok(exerciseService.findExerciseHistory(jwtService.parseUsernameFromJwt(jwtToken), id));
    }
    @GetMapping("/{id}/workouts")
    public ResponseEntity<?> findMaxWeights(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        return ResponseEntity.ok(exerciseService.findMaxWeights(jwtService.parseUsernameFromJwt(jwtToken), id));
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
