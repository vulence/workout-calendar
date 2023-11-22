package com.vule.workoutcalendar.exercise.dto;

import com.vule.workoutcalendar.musclegroup.MuscleGroup;

import java.util.Set;

public class ExerciseDto {
    private Integer id;
    private String name;
    private String description;
    private Set<MuscleGroup> muscleGroups;

    public ExerciseDto(Integer id, String name, String description, Set<MuscleGroup> muscleGroups) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.muscleGroups = muscleGroups;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MuscleGroup> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(Set<MuscleGroup> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
}
