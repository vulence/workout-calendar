package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.jwt.api.JwtServiceApi;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseControllerApi;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseServiceApi;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class WorkoutExerciseController implements WorkoutExerciseControllerApi {
    private final WorkoutExerciseServiceApi workoutExerciseServiceApi;
    private final JwtServiceApi jwtServiceApi;

    public WorkoutExerciseController(WorkoutExerciseServiceApi workoutExerciseServiceApi, JwtServiceApi jwtServiceApi) {
        this.workoutExerciseServiceApi = workoutExerciseServiceApi;
        this.jwtServiceApi = jwtServiceApi;
    }

    @Override
    @GetMapping("")
    @RequiresJwtToken
    public ResponseEntity<?> findWorkoutExercises(@RequestAttribute(name = "jwtToken") String jwtToken,
                                                  @RequestParam(name = "grouped", defaultValue = "false") String grouped,
                                                  @PathVariable Integer id) {
        if (grouped.equals("true"))
            return ResponseEntity.ok(workoutExerciseServiceApi.getGroupedWorkoutExercises(jwtServiceApi.parseUserIdFromJwt(jwtToken), id));

        return ResponseEntity.ok(workoutExerciseServiceApi.getWorkoutExercises(jwtServiceApi.parseUserIdFromJwt(jwtToken), id));
    }

    @Override
    @PostMapping("")
    @RequiresJwtToken
    public ResponseEntity<Void> addWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken,
                                                   @PathVariable Integer id,
                                                   @Valid @RequestBody WorkoutExerciseDto workoutExerciseDto) {
        workoutExerciseServiceApi.addWorkoutExercise(jwtServiceApi.parseUserIdFromJwt(jwtToken), id, workoutExerciseDto);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/update")
    @RequiresJwtToken
    public ResponseEntity<Void> updateWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken,
                                               @PathVariable Integer id,
                                               @Valid @RequestBody WorkoutExercise workoutExercise) {
        workoutExerciseServiceApi.updateWorkoutExercise(jwtServiceApi.parseUserIdFromJwt(jwtToken), id, workoutExercise);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{workoutExerciseId}")
    @RequiresJwtToken
    public ResponseEntity<Void> deleteWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken,
                                                      @PathVariable Integer id,
                                                      @PathVariable Integer workoutExerciseId) {
        workoutExerciseServiceApi.deleteWorkoutExercise(jwtServiceApi.parseUserIdFromJwt(jwtToken), id, workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}
