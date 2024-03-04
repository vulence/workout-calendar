package com.vule.workoutcalendar.musclegroup;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("exercise_muscle_group")
@Getter
@Setter
public class MuscleGroupRef {

    private AggregateReference<MuscleGroup, Integer> muscleGroup;
    private String name;
}
