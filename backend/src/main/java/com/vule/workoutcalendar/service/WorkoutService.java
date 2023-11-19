package com.vule.workoutcalendar.service;

import com.vule.workoutcalendar.model.Exercise;
import com.vule.workoutcalendar.model.ExerciseDone;
import com.vule.workoutcalendar.model.MuscleGroup;
import com.vule.workoutcalendar.model.Workout;
import com.vule.workoutcalendar.model.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.model.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.model.dto.WorkoutDto;
import com.vule.workoutcalendar.repository.ExerciseRepository;
import com.vule.workoutcalendar.repository.WorkoutRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WorkoutService {
    private final WorkoutRepository workouts;
    private final ExerciseRepository exercises;

    public WorkoutService(WorkoutRepository workouts, ExerciseRepository exercises) {
        this.workouts = workouts;
        this.exercises = exercises;
    }

    public List<WorkoutDto> findAll() {
        List<Workout> allWorkouts = workouts.findAll();
        List<WorkoutDto> allWorkoutsWithMuscleGroups = new ArrayList<>();

        for (Workout w : allWorkouts) {
            List<Exercise> exercisesInWorkouts = getWorkoutDetails(w.getId());
            Set<String> muscles = new HashSet<>();

            for (Exercise e : exercisesInWorkouts) {
                for (String muscle : e.getMuscleGroupNames()) muscles.add(muscle);
            }

            WorkoutDto curWorkout = new WorkoutDto(w);
            curWorkout.setMuscleGroups(muscles);

            allWorkoutsWithMuscleGroups.add(curWorkout);
        }

        return allWorkoutsWithMuscleGroups;
    }

    public Workout findById(Integer id) {
        return workouts.findById(id).orElse(null);
    }

    public List<Exercise> getWorkoutDetails(Integer id) {
        Workout w = workouts.findById(id).orElse(null);
        List<Exercise> exercisesInWorkout = new ArrayList<>();

        for (ExerciseDone e : w.getExercisesDone()) exercisesInWorkout.add(exercises.findById(e.getExercise().getId()).get());

        return exercisesInWorkout;
    }

    public Set<String> getWorkoutMuscleGroups(Integer id) {
        Set<String> workoutMuscleGroups = new HashSet<>();
        List<Exercise> exercisesInWorkout = getWorkoutDetails(id);

        for (Exercise e : exercisesInWorkout) {
            for (String muscleGroup: e.getMuscleGroupNames()) {
                workoutMuscleGroups.add(muscleGroup);
            }
        }

        return workoutMuscleGroups;
    }

    public void create(Workout workout) {
        workouts.save(workout);
    }

    public void updateNotes(Integer workoutId, String notes) {
        if (!workouts.existsById(workoutId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found!");
        }

        Workout workout = workouts.findById(workoutId).get();
        workout.setNotes(notes);

        workouts.updateNotes(workoutId, notes);
    }

    public void updateDuration(Integer workoutId, Integer duration) {
        if (!workouts.existsById(workoutId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found!");
        }

        if (duration <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid duration!");
        }

        Workout workout = workouts.findById(workoutId).get();
        workout.setDuration(duration);

        workouts.updateDuration(workoutId, duration);
    }

    public void updateRating(Integer workoutId, Integer rating) {
        if (!workouts.existsById(workoutId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found!");
        }

        if (rating < 1 || rating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating!");
        }

        Workout workout = workouts.findById(workoutId).get();
        workout.setRating(rating);

        workouts.updateRating(workoutId, rating);
    }

    public void addWorkoutExercise(Integer workoutId, ExerciseDoneDto exerciseDoneDto) {
        ExerciseDone ed = exerciseDoneDto.toExerciseDone();
        ed.setExercise(AggregateReference.to(exercises.findById(exerciseDoneDto.getExerciseId()).get().getId()));

        Workout workout = workouts.findById(workoutId).get();
        workout.addExerciseDone(ed);

        workouts.save(workout);
    }

    public void delete(Integer id) {
        workouts.deleteById(id);
    }

    public void updateWorkoutExercise(Integer workoutId, DataGridExerciseDto dataGridExerciseDto) {
        Workout workout = workouts.findById(workoutId).get();
        ExerciseDone ed = workouts.findExerciseDone(dataGridExerciseDto.getRowId());

        ed.setWeight(dataGridExerciseDto.getWeight());
        ed.setSets(dataGridExerciseDto.getSets());
        ed.setReps(dataGridExerciseDto.getReps());

        workouts.updateExerciseDone(dataGridExerciseDto.getRowId(),
                                    dataGridExerciseDto.getWeight(),
                                    dataGridExerciseDto.getSets(),
                                    dataGridExerciseDto.getReps());
    }

    public void deleteWorkoutExercise(Integer workoutId, Integer exerciseDoneId) {
        Workout workout = workouts.findById(workoutId).get();
        ExerciseDone ed = workouts.findExerciseDone(exerciseDoneId);

        workout.removeExerciseDone(ed);

        workouts.deleteExerciseDone(exerciseDoneId);
    }
}
