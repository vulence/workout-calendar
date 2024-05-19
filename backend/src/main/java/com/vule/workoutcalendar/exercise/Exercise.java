package com.vule.workoutcalendar.exercise;

import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class Exercise {

    @Id
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;

    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }
}