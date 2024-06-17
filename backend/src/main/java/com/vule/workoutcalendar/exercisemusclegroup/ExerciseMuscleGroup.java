package com.vule.workoutcalendar.exercisemusclegroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

/**
 * Represents a joint table of the Exercise-MuscleGroup connection.
 *
 * An ExerciseMuscleGroup has an id, a primary flag, an exerciseId and a muscleGroupId.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class ExerciseMuscleGroup {

    /**
     * A unique identifier of the ExerciseMuscleGroup.
     */
    @Id private Integer id;

    /**
     * A boolean flag field that marks a muscle group as primary for the exercise.
     */
    private boolean primary;

    /**
     * An id of the exercise to which the instance refers to.
     */
    private Integer exerciseId;

    /**
     * An id of the muscleGroup to which the instance refers to.
     */
    private Integer muscleGroupId;

    /**
     * Compares two ExerciseMuscleGroups according to their ids, primary flags, exerciseIds and muscleGroupIds.
     *
     * @param o The second ExerciseMuscleGroup to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the ExerciseMuscleGroup class,
     *     and have the same ids, primary flags, exerciseIds and muscleGroupIds</li>
     *     <li><b>false</b> - if objects don't belong to the ExerciseMuscleGroup class, if the inputted object is null
     *     or if they don't have the same ids, primary flags, exerciseIds or muscleGroupIds</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseMuscleGroup that = (ExerciseMuscleGroup) o;
        return primary == that.primary && Objects.equals(id, that.id) && Objects.equals(exerciseId, that.exerciseId) && Objects.equals(muscleGroupId, that.muscleGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primary, exerciseId, muscleGroupId);
    }
}