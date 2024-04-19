package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exercise.api.ExerciseControllerApi;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
class ExerciseController implements ExerciseControllerApi {

    private final ExerciseService exerciseService;

    private final JwtService jwtService;

    public ExerciseController(ExerciseService exerciseService, JwtService jwtService) {
        this.exerciseService = exerciseService;
        this.jwtService = jwtService;
    }

    @Override
    @GetMapping("")
    public List<Exercise> findAll() {
        return exerciseService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Integer id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/muscleGroups/{muscleGroupId}")
    List<Exercise> findByMuscleGroup(@PathVariable Integer muscleGroupId) {
        return exerciseService.findByMuscleGroup(muscleGroupId);
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

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @RequiresJwtToken
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.create(exercise);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
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
