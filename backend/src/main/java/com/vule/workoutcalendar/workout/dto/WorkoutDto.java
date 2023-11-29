package com.vule.workoutcalendar.workout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vule.workoutcalendar.workout.Workout;

import java.time.LocalDateTime;
import java.util.Set;

public class WorkoutDto {
    private Integer id;
    private String title;
    @JsonFormat(pattern="dd-MM-YYYY")
    private LocalDateTime date;
    private String notes;
    private Integer duration;
    private Integer rating;
    private Boolean finished;
    private Set<String> muscleGroups;

    public WorkoutDto() {}

    public WorkoutDto(Workout workout) {
        this.id = workout.getId();
        this.title = workout.getTitle();
        this.date = workout.getDate();
        this.notes = workout.getNotes();
        this.duration = workout.getDuration();
        this.rating = workout.getRating();
        this.finished = workout.getFinished();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Set<String> getMuscleGroups() {
        return muscleGroups;
    }

    public void setMuscleGroups(Set<String> muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
}
