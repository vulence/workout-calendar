package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.workout.impl.WorkoutRepository;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseServiceApi;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class WorkoutExerciseService implements WorkoutExerciseServiceApi {
    private final WorkoutRepository workouts;

    private final ExerciseRepository exercises;

    private final WorkoutExerciseRepository workoutExercises;

    public WorkoutExerciseService(WorkoutRepository workouts, ExerciseRepository exercises, WorkoutExerciseRepository workoutExercises) {
        this.workouts = workouts;
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
    }

    private void checkWorkoutBelongsToUser(Integer userId, Integer workoutId) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");
    }

    @Override
    public List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer workoutId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        List<WorkoutExercise> allExercises = workoutExercises.findAllByWorkoutId(workoutId).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        return allExercises;
    }

    @Override
    public List<GroupedExerciseDto> getGroupedWorkoutExercises(Integer userId, Integer workoutId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        List<WorkoutExercise> allExercises = workoutExercises.findAllByWorkoutId(workoutId).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        Map<Integer, GroupedExerciseDto> groupedExercises = new HashMap<>();

        for (WorkoutExercise we : allExercises) {
            if (!groupedExercises.containsKey(we.getExerciseId())) {
                String exerciseName = exercises.findExerciseName(we.getExerciseId());
                GroupedExerciseDto groupedExerciseDto = new GroupedExerciseDto(exerciseName, new ArrayList<>());
                groupedExercises.put(we.getExerciseId(), groupedExerciseDto);
            }

            Map<String, Integer> details = new HashMap<>();
            details.put("weight", we.getWeight());
            details.put("sets", we.getSets());
            details.put("reps", we.getReps());
            details.put("id", we.getId());
            groupedExercises.get(we.getExerciseId()).details().add(details);
        }

        return new ArrayList<>(groupedExercises.values());
    }

    @Override
    public void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise we = WorkoutExercise.from(workoutExerciseDto);
        we.setWorkoutId(workoutId);

        workoutExercises.save(we);
    }

    @Override
    public void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise existingWE = workoutExercises.findById(workoutExercise.getId()).get();
        existingWE.setExerciseName(workoutExercise.getExerciseName());
        existingWE.setWeight(workoutExercise.getWeight());
        existingWE.setSets(workoutExercise.getSets());
        existingWE.setReps(workoutExercise.getReps());
        existingWE.setCompleted(workoutExercise.isCompleted());

        workoutExercises.save(existingWE);
    }

    @Override
    public void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        WorkoutExercise we = workoutExercises.findById(WorkoutExerciseId).get();
        workoutExercises.delete(we);
    }
}