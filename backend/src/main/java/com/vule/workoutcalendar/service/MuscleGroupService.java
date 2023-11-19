package com.vule.workoutcalendar.service;

import com.vule.workoutcalendar.model.MuscleGroup;
import com.vule.workoutcalendar.repository.MuscleGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupService {
    private final MuscleGroupRepository muscleGroups;

    public MuscleGroupService(MuscleGroupRepository muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    public List<MuscleGroup> findAll() {
        return muscleGroups.findAll();
    }

    public MuscleGroup findById(Integer id) {
        return muscleGroups.findById(id).orElse(null);
    }

    public void create(MuscleGroup muscleGroup) {
        muscleGroups.save(muscleGroup);
    }

    public void delete(Integer id) {
        muscleGroups.deleteById(id);
    }
}
