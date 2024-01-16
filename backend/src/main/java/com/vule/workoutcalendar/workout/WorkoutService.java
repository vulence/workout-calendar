package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.WorkoutExerciseRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
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

    public List<Workout> findAll(Integer userId, int page, int size) {
        if (page <= 0) return workouts.findByUserId(userId);
        else {
            int offset = (page - 1) * size;
            return workouts.findByUserIdPaged(userId, size, offset);
        }
    }

    public Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    public Workout findTodaysWorkout(Integer userId) {
        return workouts.findTodaysWorkout(userId, LocalDate.now()).orElse(null);
    }

    public List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer id) {
        if (workouts.findByIdAndUserId(id, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

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

    public void update(Integer userId, Integer workoutId, ObjectNode objectNode) {
        String field = objectNode.get("field").asText().toLowerCase();

        switch (field) {
            case "notes":
                if (objectNode.get("notes") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect JSON format.");

                workouts.updateNotes(workoutId, userId, objectNode.get("notes").asText());
                break;
            case "duration":
                if (objectNode.get("duration") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format.");

                int duration = objectNode.get("duration").asInt();
                if (duration <= 1) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid duration value.");
                workouts.updateDuration(workoutId, userId, duration);
                break;
            case "rating":
                if (objectNode.get("rating") == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid JSON format.");

                int rating = objectNode.get("rating").asInt();
                if (rating < 0 || rating > 5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating value.");
                workouts.updateRating(workoutId, userId, rating);
                break;
        }
    }

    public void updateWorkoutExerciseCompleted(Integer userId, Integer workoutId, Integer WorkoutExerciseId, Boolean completed) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise we = workoutExercises.findWorkoutExercise(WorkoutExerciseId);
        we.setCompleted(completed);

        workoutExercises.updateWorkoutExercise(we.getId(),
                we.getWeight(),
                we.getSets(),
                we.getReps(),
                we.isCompleted());
    }

    public void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise we = workoutExerciseDto.toWorkoutExercise();
        we.setWorkoutId(workoutId);
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        workoutExercises.save(we);
    }

    public void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }

    public void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        workoutExercises.updateWorkoutExercise(workoutExercise.getId(),
                workoutExercise.getWeight(),
                workoutExercise.getSets(),
                workoutExercise.getReps(),
                workoutExercise.isCompleted());
    }

    public void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise we = workoutExercises.findWorkoutExercise(WorkoutExerciseId);
        workoutExercises.delete(we);
    }
}
