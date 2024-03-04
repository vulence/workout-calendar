package com.vule.workoutcalendar.workoutexercise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class WorkoutExerciseDto {
    @NotNull
    @Positive(message = "Exercise ID has to be a positive number")
    private Integer exerciseId;

    @NotNull
    private Integer weight;

    @NotNull
    @Positive(message = "Sets have to be a positive number")
    private Integer sets;

    @NotNull
    @Positive(message = "Reps have to be a positive number")
    private Integer reps;
}
