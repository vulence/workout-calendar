package com.vule.workoutcalendar.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * Represents a gym exercise.
 *
 * An exercise has an id, a name, a description, and an external image URI.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Setter
@Getter
@NoArgsConstructor
public class Exercise {

    /**
     * A unique identifier of the exercise
     */
    @Id
    private Integer id;

    /**
     * The name of the exercise (case-insensitive)
     */
    private String name;

    /**
     * The description of the exercise
     */
    private String description;

    /**
     * A web URI of an image that displays the exercise
     */
    private String imageUrl;

    /**
     * Creates a new exercise and sets name and description to the input values
     *
     * @param name Workout name as a string
     * @param description Workout description as a string
     */
    public Exercise(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Compares the two exercises according to their names (case-insensitive and ignores whitespaces).
     * For example, "Bench press" and "benchpress" are considered equal, but "benchpress" and "bench-press" are not.
     *
     * @param o The second exercise to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the Exercise class and have the same names</li>
     *     <li><b>false</b> - if objects don't belong to the Exercise class, if the inputted object is null
     *     or if they don't have the same names</li>
     * </ul>
     */
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