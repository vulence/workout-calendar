package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository workouts;
    private final ExerciseRepository exercises;

    public WorkoutService(WorkoutRepository workouts, ExerciseRepository exercises) {
        this.workouts = workouts;
        this.exercises = exercises;
    }

    public List<Workout> findAll(Integer userId) {
        return workouts.findByUserId(userId);
    }

    public Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    public List<Exercise> getWorkoutExercises(Integer userId, Integer id) {
        Workout w = workouts.findByIdAndUserId(id, userId).orElse(null);
        List<Exercise> exercisesInWorkout = new ArrayList<>();

        for (ExerciseDone e : w.getExercisesDone()) exercisesInWorkout.add(exercises.findById(e.getExercise().getId()).get());

        return exercisesInWorkout;
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

    public void updateCompleted(Integer userId, Integer workoutId, Integer exerciseDoneId, Boolean completed) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        ExerciseDone ed = workouts.findExerciseDone(exerciseDoneId);
        ed.setCompleted(completed);

        workouts.updateExerciseDone(ed.getId(),
                ed.getWeight(),
                ed.getSets(),
                ed.getReps(),
                ed.isCompleted());
    }

    public void addWorkoutExercise(Integer userId, Integer workoutId, ExerciseDoneDto exerciseDoneDto) {
        ExerciseDone ed = exerciseDoneDto.toExerciseDone();
        ed.setExercise(AggregateReference.to(exercises.findById(exerciseDoneDto.getExerciseId()).get().getId()));

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.addExerciseDone(ed);

        workouts.save(workout);
    }

    public void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }

    public void updateWorkoutExercise(Integer userId, Integer workoutId, ExerciseDone exerciseDone) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        ExerciseDone ed = workouts.findExerciseDone(exerciseDone.getId());

        ed.setWeight(exerciseDone.getWeight());
        ed.setSets(exerciseDone.getSets());
        ed.setReps(exerciseDone.getReps());

        workouts.updateExerciseDone(ed.getId(),
                ed.getWeight(),
                ed.getSets(),
                ed.getReps(),
                ed.isCompleted());
    }

    public void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer exerciseDoneId) {
        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        ExerciseDone ed = workouts.findExerciseDone(exerciseDoneId);

        workout.removeExerciseDone(ed);

        workouts.deleteExerciseDone(exerciseDoneId);
    }
}
