package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseServiceApi;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/workout-exercise")
public class WorkoutExerciseController {
    private final WorkoutExerciseServiceApi workoutExerciseServiceApi;
    private final JwtServiceApi jwtServiceApi;

    public WorkoutExerciseController(WorkoutExerciseServiceApi workoutExerciseServiceApi, JwtServiceApi jwtServiceApi) {
        this.workoutExerciseServiceApi = workoutExerciseServiceApi;
        this.jwtServiceApi = jwtServiceApi;
    }

    @PostMapping("")
    @RequiresJwtToken
    public ResponseEntity<Void> addWorkoutExercise(@Valid @RequestBody WorkoutExercise workoutExercise) {
        workoutExerciseServiceApi.addWorkoutExercise(workoutExercise);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{workoutExerciseId}")
    @RequiresJwtToken
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable Integer workoutExerciseId) {
        workoutExerciseServiceApi.deleteWorkoutExercise(workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}
