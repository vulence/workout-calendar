package com.vule.workoutcalendar.exerciseset.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.exerciseset.ExerciseSet;
import com.vule.workoutcalendar.exerciseset.api.ExerciseSetServiceApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/exercise-set")
public class ExerciseSetController {
    private final ExerciseSetServiceApi exerciseSetServiceApi;

    public ExerciseSetController(ExerciseSetServiceApi exerciseSetServiceApi) {
        this.exerciseSetServiceApi = exerciseSetServiceApi;
    }

    @GetMapping("/{workoutExerciseId}")
    @RequiresJwtToken
    public ResponseEntity<List<ExerciseSet>> findExerciseSetsByWorkoutExerciseId(@RequestAttribute String jwtToken, @PathVariable Integer workoutExerciseId) {
        return ResponseEntity.ok(exerciseSetServiceApi.getExerciseSetsByWorkoutExerciseId(workoutExerciseId));
    }
}
