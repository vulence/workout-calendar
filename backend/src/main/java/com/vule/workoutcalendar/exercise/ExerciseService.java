package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import com.vule.workoutcalendar.musclegroup.MuscleGroupRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExerciseService {
    private final ExerciseRepository exercises;
    private final MuscleGroupRepository muscleGroups;

    public ExerciseService(ExerciseRepository exercises, MuscleGroupRepository muscleGroups) {
        this.exercises = exercises;
        this.muscleGroups = muscleGroups;
    }

    public List<ExerciseDto> findAll() {
        List<Exercise> allExercises = exercises.findAll();
        List<ExerciseDto> allExercisesMuscleGroups = new ArrayList<ExerciseDto>();

        for (Exercise e : allExercises) {
            Set<MuscleGroup> muscles = new TreeSet<>(Comparator.comparing(MuscleGroup::getName));

            for (Integer id : e.getMuscleGroupIds()) {
                muscles.add(muscleGroups.findById(id).get());
            }

            allExercisesMuscleGroups.add(new ExerciseDto(e.getId(), e.getName(), e.getDescription(), muscles));
        }


        return allExercisesMuscleGroups;
    }

    public Exercise findById(Integer id) {
        return exercises.findById(id).orElse(null);
    }

    public List<ExerciseHistoryDto> findExerciseHistory(Integer userId, Integer id) {
        return exercises.findExerciseHistory(userId, id);
    }

    public List<ExerciseHistoryDto> findMaxWeights(Integer userId, Integer id) {
        return exercises.findMaxWeights(userId, id);
    }

    public void create(Exercise exercise) {
        if (exercises.findAll().stream().noneMatch(e -> e.getName().toLowerCase().trim().equals(exercise.getName().toLowerCase().trim()))) {
            exercises.save(exercise);
        }
    }

    public void delete(Integer id) {
        exercises.deleteById(id);
    }

    public void addMuscleGroup(Integer id, Integer muscleGroupId) {
        Exercise e = exercises.findById(id).get();
        e.addMuscleGroup(muscleGroups.findById(muscleGroupId).get(), e.getName());

        exercises.save(e);
    }
    public void deleteMuscleGroup(Integer id, Integer muscleGroupId) {
        Exercise e = exercises.findById(id).get();
        e.removeMuscleGroup(muscleGroups.findById(muscleGroupId).get());

        exercises.save(e);
    }
}
