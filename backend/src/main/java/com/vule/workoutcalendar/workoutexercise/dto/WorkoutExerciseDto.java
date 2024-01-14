package com.vule.workoutcalendar.workoutexercise.dto;

import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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

    public WorkoutExercise toWorkoutExercise() {
        return new WorkoutExercise(weight, sets, reps);
    }
}
