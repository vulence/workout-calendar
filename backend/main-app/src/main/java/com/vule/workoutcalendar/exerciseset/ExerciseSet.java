package com.vule.workoutcalendar.exerciseset;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseSet {

    @Id private Integer id;
    private Integer workoutExerciseId;
    private SetType setType;
    private Integer weight;
    private Integer reps;

    /**
     * Sets the weight (in kg) with which the exercise was done to the input value
     *
     * The inputted weight cannot be less than 0
     *
     * @param weight Exercise weight as an integer
     *
     * @throws IllegalArgumentException if the entered weight is less than 0
     */
    public void setWeight(Integer weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive integer");
        }

        this.weight = weight;
    }

    /**
     * Sets the number of reps for each set to the input value
     *
     * The inputted number of reps cannot be less than 0
     *
     * @param reps Number of reps as an integer
     *
     * @throws IllegalArgumentException if the entered reps are less than 0
     */
    public void setReps(Integer reps) {
        if (reps < 0) {
            throw new IllegalArgumentException("Reps must be a positive integer");
        }

        this.reps = reps;
    }

    /**
     * Compares two ExerciseSets according to their ids, workoutExerciseIds, types, weights, and reps.
     *
     * @param o The second ExerciseSet to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the ExerciseSet class,
     *     and have the same ids, workoutExerciseIds, types, weights, and reps</li>
     *     <li><b>false</b> - if objects don't belong to the ExerciseSet class, if the inputted object is null
     *     or if they don't have the same ids, workoutExerciseIds, types, weights, and reps</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseSet that = (ExerciseSet) o;
        return Objects.equals(id, that.id) && Objects.equals(workoutExerciseId, that.workoutExerciseId) && setType == that.setType && Objects.equals(weight, that.weight) && Objects.equals(reps, that.reps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workoutExerciseId, setType, weight, reps);
    }
}
