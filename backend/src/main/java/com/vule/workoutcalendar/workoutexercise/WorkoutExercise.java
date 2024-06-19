package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Objects;

/**
 * Represents a joint table of the Workout-Exercise connection.
 *
 * A WorkoutExercise has an id, a (transient) exerciseName, weight, number of sets and reps, a completed flag,
 * a workoutId and an exerciseId.
 *
 * A WorkoutExercise contains information related to an exercise that was completed in a workout.
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
     * The weight (in kg) with which the exercise was executed.
     */
    private Integer weight;

    /**
     * The number of sets for which the exercise was done.
     */
    private Integer sets;

    /**
     * The number of reps in each set of the exercise.
     */
    private Integer reps;

    /**
     * A boolean flag field that marks whether the exercise was completed.
     */
    private boolean completed;

    /**
     * An id of the workout to which the WorkoutExercise refers to.
     */
    private Integer workoutId;

    /**
     * An id of the exercise to which the WorkoutExercise refers to.
     */
    private Integer exerciseId;

    /**
     * Sets the weight (in kg) with which the exercise was executed to the input value
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
     * Sets the number of sets with which the exercise was done to the input value
     *
     * The inputted number of sets cannot be less than 0
     *
     * @param sets Number of sets as an integer
     *
     * @throws IllegalArgumentException if the entered sets are less than 0
     */
    public void setSets(Integer sets) {
        if (sets < 0) {
            throw new IllegalArgumentException("Sets must be a positive integer");
        }

        this.sets = sets;
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
     * A factory method that returns a WorkoutExercise generated from a WorkoutExerciseDto.
     *
     * @param workoutExerciseDto The workoutExerciseDto from which a WorkoutExercise instance should be created
     * @return A WorkoutExercise object that takes fields from the workoutExerciseDto
     */
    public static WorkoutExercise from(WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise we = new WorkoutExercise();
        we.setWeight(workoutExerciseDto.getWeight());
        we.setSets(workoutExerciseDto.getSets());
        we.setReps(workoutExerciseDto.getReps());
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        return we;
    }

    /**
     * Compares two WorkoutExercises according to their completed flags, weights, sets, reps, workoutIds and exerciseIds.
     *
     * @param o The second WorkoutExercise to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the WorkoutExercise class,
     *     and have the same completed flags, weights, sets, reps, workoutIds and exerciseIds</li>
     *     <li><b>false</b> - if objects don't belong to the WorkoutExercise class, if the inputted object is null
     *     or if they don't have the same completed flags, weights, sets, reps, workoutIds and exerciseIds</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExercise that = (WorkoutExercise) o;
        return completed == that.completed && Objects.equals(weight, that.weight) && Objects.equals(sets, that.sets) && Objects.equals(reps, that.reps) && Objects.equals(workoutId, that.workoutId) && Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, sets, reps, completed, workoutId, exerciseId);
    }
}
