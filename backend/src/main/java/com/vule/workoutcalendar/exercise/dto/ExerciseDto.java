package com.vule.workoutcalendar.exercise.dto;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ExerciseDto {

    private Integer id;
    private String name;
    private String description;
    private Set<MuscleGroup> muscleGroups;
}
