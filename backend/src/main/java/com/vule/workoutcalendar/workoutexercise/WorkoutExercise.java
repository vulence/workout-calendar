package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

public class WorkoutExercise {

    @Id
    private Integer id;
    @Transient
    private String exerciseName;
    private Integer weight;
    private Integer sets;
    private Integer reps;
    private boolean completed;

    private Integer workoutId;
    private Integer exerciseId;

    public WorkoutExercise() {}

    public static WorkoutExercise from(WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise we = new WorkoutExercise();
        we.setWeight(workoutExerciseDto.getWeight());
        we.setSets(workoutExerciseDto.getSets());
        we.setReps(workoutExerciseDto.getReps());

        return we;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) {this.id = id;}

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Integer workoutId) {
        this.workoutId = workoutId;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }
}
