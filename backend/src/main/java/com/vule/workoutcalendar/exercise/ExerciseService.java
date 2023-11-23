package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.exercise.dto.ExerciseDto;
import com.vule.workoutcalendar.exercise.dto.ExerciseHistoryDto;
import com.vule.workoutcalendar.musclegroup.MuscleGroupRepository;
import com.vule.workoutcalendar.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExerciseService {
    private final ExerciseRepository exercises;
    private final MuscleGroupRepository muscleGroups;
    private final UserRepository users;

    public ExerciseService(ExerciseRepository exercises, MuscleGroupRepository muscleGroups, UserRepository users) {
        this.exercises = exercises;
        this.muscleGroups = muscleGroups;
        this.users = users;
    }

    public List<ExerciseDto> findAll() {
        List<Exercise> allExercises = exercises.findAll();
        List<ExerciseDto> allExercisesMuscleGroups = new ArrayList<ExerciseDto>();

        for (Exercise e : allExercises) {
            Set<MuscleGroup> muscles = new HashSet<>();

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

    public List<ExerciseHistoryDto> findExerciseHistory(String username, Integer id) {
        Integer userId = users.findByUsername(username).getId();

        return exercises.findExerciseHistory(userId, id);
    }

    public List<ExerciseHistoryDto> findMaxWeights(String username, Integer id) {
        Integer userId = users.findByUsername(username).getId();

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
