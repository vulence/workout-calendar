package com.vule.workoutcalendar.musclegroup;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("EXERCISE_MUSCLE_GROUP")
public class MuscleGroupRef {
    private AggregateReference<MuscleGroup, Integer> muscleGroup;
    private String name;

    public AggregateReference<MuscleGroup, Integer> getMuscleGroup() {
        return muscleGroup;
    }

    public String getName() {
        return name;
    }

    public void setMuscleGroup(AggregateReference<MuscleGroup, Integer> muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public void setName(String name) {
        this.name = name;
    }
}
