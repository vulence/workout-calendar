package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.api.MuscleGroupServiceApi;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupService implements MuscleGroupServiceApi {
    private final MuscleGroupRepository muscleGroups;

    public MuscleGroupService(MuscleGroupRepository muscleGroups) {
        this.muscleGroups = muscleGroups;
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
}
