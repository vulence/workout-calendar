package com.vule.workoutcalendar.musclegroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * Represents a muscle group
 *
 * A muscle group has an id, a name and a description.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class MuscleGroup {

    /**
     * A unique identifier of the muscle group
     */
    @Id private Integer id;

    /**
     * The name of the muscle group (case-insensitive)
     */
    private String name;

    /**
     * The description of the muscle group
     */
    private String description;

    /**
     * Creates a new muscle group and sets name and description to the input values
     *
     * @param name Muscle group name as a string
     * @param description Muscle group description as a string
     */
    public MuscleGroup(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Compares the two muscle groups according to their names (case-insensitive and ignores whitespaces).
     * For example, "lower back" and "Lowerback" are considered equal, but "lower back" and "lower-back" are not.
     *
     * @param o The second muscle group to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the MuscleGroup class and have the same names</li>
     *     <li><b>false</b> - if objects don't belong to the MuscleGroup class, if the inputted object is null
     *     or if they don't have the same names</li>
     * </ul>
     */
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
