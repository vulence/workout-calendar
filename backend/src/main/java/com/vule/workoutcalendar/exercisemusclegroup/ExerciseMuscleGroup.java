package com.vule.workoutcalendar.exercisemusclegroup;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ExerciseMuscleGroup {

    @Id private Integer id;
    private boolean primary;

    private Integer exerciseId;
    private Integer muscleGroupId;

    public ExerciseMuscleGroup() {}
}