package com.vule.workoutcalendar.model;

import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

public class ExerciseDone {

    @Id
    private Integer id;
    private Integer weight;
    private Integer sets;
    private Integer reps;
    private AggregateReference<Exercise, Integer> exercise;
    @Transient
    Workout workout;

    public ExerciseDone(Integer weight, Integer sets, Integer reps, AggregateReference<Exercise, Integer> exercise) {
        this.weight = weight;
        this.sets = sets;
        this.reps = reps;
        this.exercise = exercise;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {this.id = id;}
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

    public AggregateReference<Exercise, Integer> getExercise() {
        return exercise;
    }

    public void setExercise(AggregateReference<Exercise, Integer> exercise) {
        this.exercise = exercise;
    }
}
