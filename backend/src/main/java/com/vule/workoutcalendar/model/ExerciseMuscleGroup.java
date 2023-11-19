package com.vule.workoutcalendar.model;

import org.springframework.data.annotation.Id;

public class ExerciseMuscleGroup {
    @Id
    private Integer id;
    private Integer exerciseId;
    private Integer muscleGroupId;

    public ExerciseMuscleGroup(Integer exerciseId, Integer muscleGroupId) {
        this.exerciseId = exerciseId;
        this.muscleGroupId = muscleGroupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getMuscleGroupId() {
        return muscleGroupId;
    }

    public void setMuscleGroupId(Integer muscleGroupId) {
        this.muscleGroupId = muscleGroupId;
    }
}
