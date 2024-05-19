package com.vule.workoutcalendar.musclegroup.impl;

import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MuscleGroupMapper {

    private final MuscleGroupRepository muscleGroupRepository;

    public MuscleGroupMapper(MuscleGroupRepository muscleGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
    }

    public MuscleGroup mapToMuscleGroup(ExerciseMuscleGroup exerciseMuscleGroup) {
        return muscleGroupRepository.findById(exerciseMuscleGroup.getMuscleGroupId()).orElseThrow();
    }

    public List<MuscleGroup> mapToMuscleGroups(List<ExerciseMuscleGroup> exerciseMuscleGroupList) {
        return exerciseMuscleGroupList.stream().map(
                emg -> muscleGroupRepository.findById(emg.getMuscleGroupId())
                        .orElseThrow()
        ).toList();
    }
}
