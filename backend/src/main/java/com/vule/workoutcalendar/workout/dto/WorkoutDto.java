package com.vule.workoutcalendar.workout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vule.workoutcalendar.workout.Workout;

import java.time.LocalDateTime;
import java.util.Set;

public class WorkoutDto {
    private Integer id;
    @JsonFormat(pattern="dd-MM-YYYY")
    private LocalDateTime date;
    private String notes;
    private Integer duration;
    private Integer rating;
    private Set<String> muscleGroups;

    public WorkoutDto() {}

    public WorkoutDto(Workout workout) {
        this.id = workout.getId();
        this.date = workout.getDate();
        this.notes = workout.getNotes();
        this.duration = workout.getDuration();
        this.rating = workout.getRating();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Set<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(Set<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
}
