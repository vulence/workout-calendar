package com.vule.workoutcalendar.workoutexercise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * A DTO that is used to transfer data about a workout exercise. This includes the exerciseId,
 * the weight with which it was executed, the number of sets, and the number of reps with each set.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Setter
@Getter
public class WorkoutExerciseDto {

    /**
     * The id of the exercise that was executed.
     * Must not be null, and the id must be a positive integer.
     */
    @NotNull
    @Positive(message = "Exercise ID has to be a positive number")
    private Integer exerciseId;

    /**
     * The weight with which the exercise was executed.
     * Must not be null.
     */
    @NotNull
    private Integer weight;

    /**
     * The number of sets which were done for the exercise.
     * Must not be null, and must be a positive integer.
     */
    @NotNull
    @Positive(message = "Sets have to be a positive number")
    private Integer sets;

    /**
     * The number of reps done in each set.
     * Must not be null, and must be a positive integer.
     */
    @NotNull
    @Positive(message = "Reps have to be a positive number")
    private Integer reps;
}
