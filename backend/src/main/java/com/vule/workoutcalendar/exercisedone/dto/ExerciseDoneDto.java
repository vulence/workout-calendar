package com.vule.workoutcalendar.exercisedone.dto;

import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ExerciseDoneDto {
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

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
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

    public ExerciseDone toExerciseDone() {
        return new ExerciseDone(weight, sets, reps);
    }
}
