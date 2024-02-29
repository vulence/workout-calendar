package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
class ExerciseController {
    private final ExerciseService exerciseService;

    private final JwtService jwtService;

    ExerciseController(ExerciseService exerciseService, JwtService jwtService) {
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    List<Exercise> findAll() {
        return exerciseService.findAll();
    }

    @GetMapping("/muscleGroups/{muscleGroupId}")
    List<Exercise> findByMuscleGroup(@PathVariable Integer muscleGroupId) {
        return exerciseService.findByMuscleGroup(muscleGroupId);
    }

    @GetMapping("/{id}")
    Exercise findById(@PathVariable Integer id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/{id}/exerciseHistory")
    @RequiresJwtToken
    ResponseEntity<?> findExerciseHistory(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(exerciseService.findExerciseHistory(jwtService.parseUserIdFromJwt(jwtToken), id));
    }
    @GetMapping("/{id}/workouts")
    @RequiresJwtToken
    ResponseEntity<?> findMaxWeights(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(exerciseService.findMaxWeights(jwtService.parseUserIdFromJwt(jwtToken), id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @RequiresJwtToken
    void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.create(exercise);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    void delete(@PathVariable Integer id) {
        exerciseService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/muscleGroups/new")
    @RequiresJwtToken
    void addMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.addMuscleGroup(id, muscleGroupId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/muscleGroups")
    @RequiresJwtToken
    void deleteMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.deleteMuscleGroup(id, muscleGroupId);
    }
}
