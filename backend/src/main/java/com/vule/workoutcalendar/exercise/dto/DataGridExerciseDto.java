package com.vule.workoutcalendar.exercise.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class DataGridExerciseDto {
    @NotNull
    @Positive(message = "Row ID has to be a positive number")
    private Integer rowId;
    @NotNull
    @Positive(message = "Weight has to be a positive number")
    private Integer weight;
    @NotNull
    @Positive(message = "Sets have to be a positive number")
    private Integer sets;
    @NotNull
    @Positive(message = "Reps have to be a positive number")
    private Integer reps;

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }
}
