package com.vule.workoutcalendar.musclegroup;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class MuscleGroupService {
    private final MuscleGroupRepository muscleGroups;

    MuscleGroupService(MuscleGroupRepository muscleGroups) {
        this.muscleGroups = muscleGroups;
    }

    List<MuscleGroup> findAll() {
        return muscleGroups.findAll();
    }

    MuscleGroup findById(Integer id) {
        return muscleGroups.findById(id).orElse(null);
    }

    void create(MuscleGroup muscleGroup) {
        muscleGroups.save(muscleGroup);
    }

    void delete(Integer id) {
        muscleGroups.deleteById(id);
    }
}
