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
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final JwtService jwtService;

    public ExerciseController(ExerciseService exerciseService, JwtService jwtService) {
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    @RequiresJwtToken
    public List<ExerciseDto> findAll() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    @RequiresJwtToken
    public Exercise findById(@PathVariable Integer id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/{id}/exerciseHistory")
    @RequiresJwtToken
    public ResponseEntity<?> findExerciseHistory(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(exerciseService.findExerciseHistory(jwtService.parseUserIdFromJwt(jwtToken), id));
    }
    @GetMapping("/{id}/workouts")
    @RequiresJwtToken
    public ResponseEntity<?> findMaxWeights(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(exerciseService.findMaxWeights(jwtService.parseUserIdFromJwt(jwtToken), id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    @RequiresJwtToken
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.create(exercise);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
        exerciseService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/muscleGroups/new")
    @RequiresJwtToken
    public void addMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.addMuscleGroup(id, muscleGroupId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/muscleGroups")
    @RequiresJwtToken
    public void deleteMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.deleteMuscleGroup(id, muscleGroupId);
    }
}
