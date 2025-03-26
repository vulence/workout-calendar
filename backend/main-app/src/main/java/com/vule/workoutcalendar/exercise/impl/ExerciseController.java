package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.api.ExerciseServiceApi;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseServiceApi exerciseServiceApi;

    public ExerciseController(ExerciseServiceApi exerciseServiceApi) {
        this.exerciseServiceApi = exerciseServiceApi;
    }

    @GetMapping("")
    public List<Exercise> findAll() {
        return exerciseServiceApi.findAll();
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Integer id) {
        return exerciseServiceApi.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    @RequiresJwtToken
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseServiceApi.create(exercise);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
        exerciseServiceApi.delete(id);
    }
}
