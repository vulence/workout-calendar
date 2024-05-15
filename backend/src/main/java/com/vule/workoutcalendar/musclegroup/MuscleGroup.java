package com.vule.workoutcalendar.musclegroup;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class    MuscleGroup {

    @Id private Integer id;
    private String name;
    private String description;
    private Integer exerciseCount;

    public MuscleGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
