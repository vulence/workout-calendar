package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.api.MuscleGroupControllerApi;
import com.vule.workoutcalendar.musclegroup.api.MuscleGroupServiceApi;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MuscleGroupController implements MuscleGroupControllerApi {
    private final MuscleGroupServiceApi muscleGroupServiceApi;

    public MuscleGroupController(MuscleGroupServiceApi muscleGroupServiceApi) {
        this.muscleGroupServiceApi = muscleGroupServiceApi;
    }

    @Override
    @GetMapping("")
    public List<MuscleGroup> findAll() {
        return muscleGroupServiceApi.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public MuscleGroup findById(@PathVariable Integer id) {
        return muscleGroupServiceApi.findById(id);
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    @RequiresJwtToken
    public void create(@Valid @RequestBody MuscleGroup muscleGroup) {
        muscleGroupServiceApi.create(muscleGroup);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @RequiresJwtToken
    public void delete(@PathVariable Integer id) {
        muscleGroupServiceApi.delete(id);
    }

    @Override
    @GetMapping("/exercises/{exerciseName}/primary")
    public MuscleGroup findPrimaryMuscleGroupForExercise(@PathVariable String exerciseName) {
        return muscleGroupServiceApi.findPrimaryMuscleGroupForExercise(exerciseName);
    }

    @Override
    @GetMapping("/exercises/{exerciseName}")
    public List<MuscleGroup> findMuscleGroupsForExercise(@PathVariable String exerciseName) {
        return muscleGroupServiceApi.findMuscleGroupsForExercise(exerciseName);
    }
}
