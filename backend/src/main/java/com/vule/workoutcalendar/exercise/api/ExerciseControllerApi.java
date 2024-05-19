package com.vule.workoutcalendar.exercise.api;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/exercises")
public interface ExerciseControllerApi {

    List<Exercise> findAll();

    Exercise findById(Integer id);

    void create(Exercise exercise);

    void delete(Integer id);

    List<Exercise> findAllExercisesByMuscleGroup(String muscleGroupName);
}
