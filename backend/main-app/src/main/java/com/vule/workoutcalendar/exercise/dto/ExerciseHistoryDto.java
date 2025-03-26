package com.vule.workoutcalendar.exercise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * A DTO used to transfer history data about an exercise. This includes a unique ID, the date when it was done,
 * the weight in kgs, the number of sets and reps.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Setter
@Getter
public class ExerciseHistoryDto {
    private Integer id;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime date;
    private Integer weight;
    private Integer sets;
    private Integer reps;

    public void setWeight(Integer weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive integer");
        }

        this.weight = weight;
    }

    public void setSets(Integer sets) {
        if (sets < 0) {
            throw new IllegalArgumentException("Sets must be a positive integer");
        }

        this.sets = sets;
    }

    public void setReps(Integer reps) {
        if (reps < 0) {
            throw new IllegalArgumentException("Reps must be a positive integer");
        }

        this.reps = reps;
    }
}
