package com.vule.workoutcalendar.workoutexercise.api;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/workouts/{id}/exercises")
public interface WorkoutExerciseControllerApi {

    ResponseEntity<?> findWorkoutExercises(String jwtToken, String grouped, Integer id);

    ResponseEntity<Void> addWorkoutExercise(String jwtToken, Integer id, WorkoutExerciseDto workoutExerciseDto);

    ResponseEntity<Void> updateWorkoutExercise(String jwtToken, Integer id, WorkoutExercise workoutExercise);

    ResponseEntity<Void> deleteWorkoutExercise(String jwtToken, Integer id, Integer workoutExerciseId);
}
