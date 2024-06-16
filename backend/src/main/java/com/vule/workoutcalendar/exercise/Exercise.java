package com.vule.workoutcalendar.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercise exercise = (Exercise) o;
        return Objects.equals(name.replaceAll("\\s+", "").toLowerCase(), exercise.name.replaceAll("\\s+", "").toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}