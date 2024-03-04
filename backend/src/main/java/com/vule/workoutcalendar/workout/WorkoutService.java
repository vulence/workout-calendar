package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.WorkoutExerciseRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
class WorkoutService {
    private final WorkoutRepository workouts;

    WorkoutService(WorkoutRepository workouts) {
        this.workouts = workouts;
    }

    List<Workout> findAll(Integer userId, int page, int size, String direction) {
        if (page <= 0) return workouts.findByUserId(userId, PageRequest.of(0, Integer.MAX_VALUE));
        else return workouts.findByUserId(userId, PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(direction), "DATE")));
    }

    Workout findById(Integer id, Integer userId) {
        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    Workout findTodaysWorkout(Integer userId) {
        return workouts.findTodaysWorkout(userId, LocalDate.now()).orElse(null);
    }

    int getWorkoutCount(Integer userId) {
        return workouts.getWorkoutCount(userId);
    }

    void create(Integer userId, Workout workout) {
        workout.setUserId(userId);

        workouts.save(workout);
    }

    void update(Integer userId, Integer workoutId, ObjectNode objectNode) {
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

    void delete(Integer userId, Integer id) {
        workouts.deleteByIdAndUserId(id, userId);
    }
}
