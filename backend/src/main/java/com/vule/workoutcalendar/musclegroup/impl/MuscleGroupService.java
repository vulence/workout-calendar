package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.exercisemusclegroup.api.ExerciseMuscleGroupServiceApi;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.api.MuscleGroupServiceApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupService implements MuscleGroupServiceApi {

    private final MuscleGroupRepository muscleGroups;
    private final ExerciseMuscleGroupServiceApi exerciseMuscleGroupServiceApi;
    private final MuscleGroupMapper muscleGroupMapper;

    public MuscleGroupService(MuscleGroupRepository muscleGroups, ExerciseMuscleGroupServiceApi exerciseMuscleGroupServiceApi, MuscleGroupMapper muscleGroupMapper) {
        this.muscleGroups = muscleGroups;
        this.exerciseMuscleGroupServiceApi = exerciseMuscleGroupServiceApi;
        this.muscleGroupMapper = muscleGroupMapper;
    }

    @Override
    public List<MuscleGroup> findAll() {
        return muscleGroups.findAll();
    }

    @Override
    public MuscleGroup findById(Integer id) {
        return muscleGroups.findById(id).orElse(null);
    }

    @Override
    public void create(MuscleGroup muscleGroup) {
        muscleGroups.save(muscleGroup);
    }

    @Override
    public void delete(Integer id) {
        muscleGroups.deleteById(id);
    }

    @Override
    public MuscleGroup findPrimaryMuscleGroupForExercise(String exerciseName) {
        return muscleGroupMapper.mapToMuscleGroup(exerciseMuscleGroupServiceApi.findPrimaryMuscleGroupForExercise(exerciseName));
    }

    @Override
    public List<MuscleGroup> findMuscleGroupsForExercise(String exerciseName) {
        return muscleGroupMapper.mapToMuscleGroups(exerciseMuscleGroupServiceApi.findAllMuscleGroupsForExercise(exerciseName));
    }
}
