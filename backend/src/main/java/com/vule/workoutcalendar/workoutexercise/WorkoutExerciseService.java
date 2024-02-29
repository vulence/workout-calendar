package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.exercise.ExerciseRepository;
import com.vule.workoutcalendar.workout.WorkoutRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
class WorkoutExerciseService {
    private final WorkoutRepository workouts;

    private final ExerciseRepository exercises;

    private final WorkoutExerciseRepository workoutExercises;

    WorkoutExerciseService(WorkoutRepository workouts, ExerciseRepository exercises, WorkoutExerciseRepository workoutExercises) {
        this.workouts = workouts;
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
    }

    private void checkWorkoutBelongsToUser(Integer userId, Integer workoutId) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");
    }

    List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer workoutId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        List<WorkoutExercise> allExercises = workoutExercises.findAllByWorkoutId(workoutId).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        return allExercises;
    }

    void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise we = WorkoutExercise.from(workoutExerciseDto);
        we.setWorkoutId(workoutId);
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        workoutExercises.save(we);
    }

    void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise existingWE = workoutExercises.findById(workoutExercise.getId()).get();
        existingWE.setExerciseName(workoutExercise.getExerciseName());
        existingWE.setWeight(workoutExercise.getWeight());
        existingWE.setSets(workoutExercise.getSets());
        existingWE.setReps(workoutExercise.getReps());
        existingWE.setCompleted(workoutExercise.isCompleted());

        workoutExercises.save(existingWE);
    }

    void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise we = workoutExercises.findById(WorkoutExerciseId).get();
        workoutExercises.delete(we);
    }
}