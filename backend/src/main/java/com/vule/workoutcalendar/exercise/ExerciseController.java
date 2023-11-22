package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercises")
@CrossOrigin
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    public List<ExerciseDto> findAll() {
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    public Exercise findById(@PathVariable Integer id) {
        return exerciseService.findById(id);
    }

    @GetMapping("/{id}/exerciseHistory")
    public List<ExerciseHistoryDto> findExerciseHistory(@PathVariable Integer id) {
        return exerciseService.findExerciseHistory(id);
    }
    @GetMapping("/{id}/workouts")
    public List<ExerciseHistoryDto> findMaxWeights(@PathVariable Integer id) {
        return exerciseService.findMaxWeights(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/new")
    public void create(@Valid @RequestBody Exercise exercise) {
        exerciseService.create(exercise);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        exerciseService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/muscleGroups/new")
    public void addMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.addMuscleGroup(id, muscleGroupId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}/muscleGroups")
    public void deleteMuscleGroup(@PathVariable Integer id, @RequestBody Integer muscleGroupId) {
        exerciseService.deleteMuscleGroup(id, muscleGroupId);
    }
}
