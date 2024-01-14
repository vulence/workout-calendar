package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.WorkoutExerciseRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workouts;
    private final ExerciseRepository exercises;
    private final WorkoutExerciseRepository workoutExercises;

    public WorkoutService(WorkoutRepository workouts, ExerciseRepository exercises, WorkoutExerciseRepository workoutExercises) {
        this.workouts = workouts;
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
    }

    public List<Workout> findAll(Integer userId) {
        return workouts.findByUserId(userId);
    }

    public Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    public Workout findTodaysWorkout(Integer userId) {
        return workouts.findTodaysWorkout(userId, LocalDate.now()).orElse(null);
    }

    public List<WorkoutExercise> getWorkoutExercises(Integer id) {
        List<WorkoutExercise> allExercises = workoutExercises.findWorkoutExercises(id).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        return allExercises;
    }

    public void create(Integer userId, Workout workout) {
        workout.setuserId(userId);

        workouts.save(workout);
    }

    public void updateNotes(Integer userId, Integer workoutId, String notes) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setNotes(notes);

        workouts.updateNotes(workoutId, notes);
    }

    public void updateDuration(Integer userId, Integer workoutId, Integer duration) {
        if (duration <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid duration!");
        }

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setDuration(duration);

        workouts.updateDuration(workoutId, duration);
    }

    public void updateRating(Integer userId, Integer workoutId, Integer rating) {
        if (rating < 0 || rating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating!");
        }

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setRating(rating);

        workouts.updateRating(workoutId, rating);
    }

    public void updateCompleted(Integer userId, Integer workoutId, Integer WorkoutExerciseId, Boolean completed) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        WorkoutExercise we = workouts.findWorkoutExercise(WorkoutExerciseId);
        we.setCompleted(completed);

        workouts.updateWorkoutExercise(we.getId(),
                we.getWeight(),
                we.getSets(),
                we.getReps(),
                we.isCompleted());
    }

    public void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise we = workoutExerciseDto.toWorkoutExercise();
        we.setWorkoutId(workoutId);
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        workoutExercises.save(we);
    }

    public void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }

    public void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        WorkoutExercise we = workouts.findWorkoutExercise(workoutExercise.getId());

        we.setWeight(workoutExercise.getWeight());
        we.setSets(workoutExercise.getSets());
        we.setReps(workoutExercise.getReps());
        we.setCompleted(false);

        workouts.updateWorkoutExercise(we.getId(),
                we.getWeight(),
                we.getSets(),
                we.getReps(),
                we.isCompleted());
    }

    public void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId) {
        WorkoutExercise we = workouts.findWorkoutExercise(WorkoutExerciseId);

        workoutExercises.delete(we);
    }
}
