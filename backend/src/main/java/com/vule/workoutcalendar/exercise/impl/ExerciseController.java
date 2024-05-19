package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.api.ExerciseControllerApi;
import com.vule.workoutcalendar.exercise.api.ExerciseServiceApi;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ExerciseController implements ExerciseControllerApi {

    private final ExerciseServiceApi exerciseServiceApi;

    private final JwtServiceApi jwtServiceApi;

    public ExerciseController(ExerciseServiceApi exerciseServiceApi, JwtServiceApi jwtServiceApi) {
        this.exerciseServiceApi = exerciseServiceApi;
        this.jwtServiceApi = jwtServiceApi;
    }

    @Override
    @GetMapping("")
    public List<Exercise> findAll() {
        return exerciseServiceApi.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Integer id) {
        return exerciseServiceApi.findById(id);
    }

//    @GetMapping("/muscleGroups/{muscleGroupId}")
//    List<Exercise> findByMuscleGroup(@PathVariable Integer muscleGroupId) {
//        return exerciseServiceApi.findByMuscleGroup(muscleGroupId);
//    }
//
//    @GetMapping("/{id}/exerciseHistory")
//    @RequiresJwtToken
//    ResponseEntity<?> findExerciseHistory(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
//        return ResponseEntity.ok(exerciseServiceApi.findExerciseHistory(jwtServiceApi.parseUserIdFromJwt(jwtToken), id));
//    }
//    @GetMapping("/{id}/workouts")
//    @RequiresJwtToken
//    ResponseEntity<?> findMaxWeights(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
//        return ResponseEntity.ok(exerciseServiceApi.findMaxWeights(jwtServiceApi.parseUserIdFromJwt(jwtToken), id));
//    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @RequiresJwtToken
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseServiceApi.create(exercise);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
        exerciseServiceApi.delete(id);
    }

    @Override
    @GetMapping("/muscle-groups/{muscleGroupName}")
    public List<Exercise> findAllExercisesByMuscleGroup(@PathVariable String muscleGroupName) {
        return exerciseServiceApi.findAllExercisesByMuscleGroup(muscleGroupName);
    }

//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PutMapping("/{id}/muscleGroups/new")
//    @RequiresJwtToken
//    void addMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
//        exerciseServiceApi.addMuscleGroup(id, muscleGroupId);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}/muscleGroups")
//    @RequiresJwtToken
//    void deleteMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
//        exerciseServiceApi.deleteMuscleGroup(id, muscleGroupId);
//    }
}
