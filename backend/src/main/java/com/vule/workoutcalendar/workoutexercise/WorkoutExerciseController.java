package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.annotation.RequiresJwtToken;
import com.vule.workoutcalendar.jwt.JwtService;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workouts/{id}/exercises")
@CrossOrigin
public class WorkoutExerciseController {
    private final WorkoutExerciseService workoutExerciseService;
    private final JwtService jwtService;

    public WorkoutExerciseController(WorkoutExerciseService workoutExerciseService, JwtService jwtService) {
        this.workoutExerciseService = workoutExerciseService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    @RequiresJwtToken
    public ResponseEntity<?> findWorkoutExercises(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id) {
        return ResponseEntity.ok(workoutExerciseService.getWorkoutExercises(jwtService.parseUserIdFromJwt(jwtToken), id));
    }

    @PostMapping("")
    @RequiresJwtToken
    public ResponseEntity<?> addWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody WorkoutExerciseDto workoutExerciseDto) {
        workoutExerciseService.addWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExerciseDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    @RequiresJwtToken
    public ResponseEntity<?> updateWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @Valid @RequestBody WorkoutExercise workoutExercise) {
        workoutExerciseService.updateWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExercise);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("")
    @RequiresJwtToken
    public ResponseEntity<?> deleteWorkoutExercise(@RequestAttribute(name = "jwtToken") String jwtToken, @PathVariable Integer id, @RequestBody Integer workoutExerciseId) {
        workoutExerciseService.deleteWorkoutExercise(jwtService.parseUserIdFromJwt(jwtToken), id, workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}
