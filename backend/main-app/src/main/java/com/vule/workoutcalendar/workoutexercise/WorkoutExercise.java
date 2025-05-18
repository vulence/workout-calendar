package com.vule.workoutcalendar.workoutexercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Objects;

/**
 * Represents a joint table of the Workout-Exercise connection.
 *
 * A WorkoutExercise has an id, a (transient) exerciseName, a workoutId and an exerciseId.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class WorkoutExercise {

    /**
     * A unique identifier of the WorkoutExercise.
     */
    @Id private Integer id;

    /**
     * The name of the exercise that the WorkoutExercise refers to (transient).
     */
    @Transient private String exerciseName;

    /**
     * An id of the workout to which the WorkoutExercise refers to.
     */
    private Integer workoutId;

    /**
     * An id of the exercise to which the WorkoutExercise refers to.
     */
    private Integer exerciseId;

    /**
     * Compares two WorkoutExercises according to their ids, workoutIds and exerciseIds.
     *
     * @param o The second WorkoutExercise to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the WorkoutExercise class,
     *     and have the same ids, workoutIds and exerciseIds</li>
     *     <li><b>false</b> - if objects don't belong to the WorkoutExercise class, if the inputted object is null
     *     or if they don't have the same ids, workoutIds and exerciseIds</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExercise that = (WorkoutExercise) o;
        return Objects.equals(id, that.id) && Objects.equals(workoutId, that.workoutId) && Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workoutId, exerciseId);
    }
}
