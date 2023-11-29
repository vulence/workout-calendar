package com.vule.workoutcalendar.workout;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import com.vule.workoutcalendar.exercise.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.user.UserRepository;
import com.vule.workoutcalendar.workout.dto.WorkoutDto;
import com.vule.workoutcalendar.exercise.ExerciseRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WorkoutService {
    private final WorkoutRepository workouts;
    private final ExerciseRepository exercises;
    private final UserRepository users;

    public WorkoutService(WorkoutRepository workouts, ExerciseRepository exercises, UserRepository users) {
        this.workouts = workouts;
        this.exercises = exercises;
        this.users = users;
    }

    public List<WorkoutDto> findAll(String username) {
        Integer userId = users.findByUsername(username).getId();

        List<Workout> allWorkouts = workouts.findByUserId(userId);
        List<WorkoutDto> allWorkoutsWithMuscleGroups = new ArrayList<>();

        for (Workout w : allWorkouts) {
            List<Exercise> exercisesInWorkouts = getWorkoutDetails(username, w.getId());
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

    public Workout findById(String username, Integer id) {
        Integer userId = users.findByUsername(username).getId();

        return workouts.findByIdAndUserId(id, userId).orElse(null);
    }

    public List<Exercise> getWorkoutDetails(String username, Integer id) {
        Integer userId = users.findByUsername(username).getId();

        Workout w = workouts.findByIdAndUserId(id, userId).orElse(null);
        List<Exercise> exercisesInWorkout = new ArrayList<>();

        for (ExerciseDone e : w.getExercisesDone()) exercisesInWorkout.add(exercises.findById(e.getExercise().getId()).get());

        return exercisesInWorkout;
    }

    public Set<String> getWorkoutMuscleGroups(String username, Integer id) {
        Set<String> workoutMuscleGroups = new HashSet<>();
        List<Exercise> exercisesInWorkout = getWorkoutDetails(username, id);

        for (Exercise e : exercisesInWorkout) {
            workoutMuscleGroups.addAll(e.getMuscleGroupNames());
        }

        return workoutMuscleGroups;
    }

    public void create(String username, Workout workout) {
        workout.setuserId(users.findByUsername(username).getId());

        workouts.save(workout);
    }

    public void updateNotes(String username, Integer workoutId, String notes) {
        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setNotes(notes);

        workouts.updateNotes(workoutId, notes);
    }

    public void updateDuration(String username, Integer workoutId, Integer duration) {
        if (duration <= 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid duration!");
        }

        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setDuration(duration);

        workouts.updateDuration(workoutId, duration);
    }

    public void updateRating(String username, Integer workoutId, Integer rating) {
        if (rating < 1 || rating > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid rating!");
        }

        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.setRating(rating);

        workouts.updateRating(workoutId, rating);
    }

    public void updateFinished(String username, Integer workoutId) {
        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();

        workout.setFinished(!workout.getFinished());

        workouts.updateFinished(workoutId, workout.getFinished() ? 1 : 0);
    }

    public void addWorkoutExercise(String username, Integer workoutId, ExerciseDoneDto exerciseDoneDto) {
        Integer userId = users.findByUsername(username).getId();

        ExerciseDone ed = exerciseDoneDto.toExerciseDone();
        ed.setExercise(AggregateReference.to(exercises.findById(exerciseDoneDto.getExerciseId()).get().getId()));

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        workout.addExerciseDone(ed);

        workouts.save(workout);
    }

    public void delete(String username, Integer id) {
        Integer userId = users.findByUsername(username).getId();

        workouts.deleteByIdAndUserId(id, userId);
    }

    public void updateWorkoutExercise(String username, Integer workoutId, DataGridExerciseDto dataGridExerciseDto) {
        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        ExerciseDone ed = workouts.findExerciseDone(dataGridExerciseDto.getRowId());

        ed.setWeight(dataGridExerciseDto.getWeight());
        ed.setSets(dataGridExerciseDto.getSets());
        ed.setReps(dataGridExerciseDto.getReps());

        workouts.updateExerciseDone(dataGridExerciseDto.getRowId(),
                                    dataGridExerciseDto.getWeight(),
                                    dataGridExerciseDto.getSets(),
                                    dataGridExerciseDto.getReps());
    }

    public void deleteWorkoutExercise(String username, Integer workoutId, Integer exerciseDoneId) {
        Integer userId = users.findByUsername(username).getId();

        Workout workout = workouts.findByIdAndUserId(workoutId, userId).get();
        ExerciseDone ed = workouts.findExerciseDone(exerciseDoneId);

        workout.removeExerciseDone(ed);

        workouts.deleteExerciseDone(exerciseDoneId);
    }
}
