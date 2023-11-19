package com.vule.workoutcalendar.controller;

import com.vule.workoutcalendar.model.Exercise;
import com.vule.workoutcalendar.model.Workout;
import com.vule.workoutcalendar.model.dto.DataGridExerciseDto;
import com.vule.workoutcalendar.model.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.model.dto.WorkoutDto;
import com.vule.workoutcalendar.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;

    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @GetMapping("")
    public List<WorkoutDto> findAll() {
        return workoutService.findAll();
    }

    @GetMapping("/{id}")
    public Workout findById(@PathVariable Integer id) {
        return workoutService.findById(id);
    }

    @GetMapping("/{id}/exercises")
    public List<Exercise> getWorkoutDetails(@PathVariable Integer id) {
        return workoutService.getWorkoutDetails(id);
    }

    @GetMapping("/{id}/muscleGroups")
    public Set<String> getWorkoutMuscleGroups(@PathVariable Integer id) {
        return workoutService.getWorkoutMuscleGroups(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void create(@Valid @RequestBody Workout workout) {
        workoutService.create(workout);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setNotes")
    public void updateNotes(@PathVariable Integer id, @RequestBody String notes) {
        workoutService.updateNotes(id, notes);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setDuration")
    public void updateDuration(@PathVariable Integer id, @RequestBody Integer duration) {
        workoutService.updateDuration(id, duration);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/setRating")
    public void updateRating(@PathVariable Integer id, @RequestBody Integer rating) {
        workoutService.updateRating(id, rating);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        workoutService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/exercises/new")
    public void addWorkoutExercise(@PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        workoutService.addWorkoutExercise(id, exerciseDoneDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/exercises/update")
    public void updateWorkoutExercise(@PathVariable Integer id, @Valid @RequestBody DataGridExerciseDto dataGridExerciseDto) {
        workoutService.updateWorkoutExercise(id, dataGridExerciseDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/exercises")
    public void deleteWorkoutExercise(@PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        workoutService.deleteWorkoutExercise(id, exerciseDoneId);
    }
}