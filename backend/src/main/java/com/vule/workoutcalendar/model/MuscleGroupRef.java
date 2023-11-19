package com.vule.workoutcalendar.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

@Table("EXERCISE_MUSCLE_GROUP")
public class MuscleGroupRef {
    AggregateReference<MuscleGroup, Integer> muscleGroup;
    String name;

    public AggregateReference<MuscleGroup, Integer> getMuscleGroup() {
        return muscleGroup;
    }

    public String getName() {
        return name;
    }
}
