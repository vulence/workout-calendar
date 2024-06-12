package com.vule.workoutcalendar.musclegroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class MuscleGroup {

    @Id private Integer id;
    private String name;
    private String description;

    public MuscleGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuscleGroup muscleGroup = (MuscleGroup) o;
        return Objects.equals(name.replaceAll("\\s+", "").toLowerCase(), muscleGroup.name.replaceAll("\\s+", "").toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
