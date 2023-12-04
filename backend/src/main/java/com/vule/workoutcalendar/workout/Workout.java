package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Workout {
    @Id
    private Integer id;
    private String title;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime date;
    private String notes;
    private Integer duration;
    private Integer rating;
    private Integer userId;
    private Set<ExerciseDone> exercisesDone = new HashSet<>();

    public Workout(String title, LocalDateTime date, String notes, Integer duration, Integer rating) {
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.duration = duration;
        this.rating = rating;
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

    public Set<ExerciseDone> getExercisesDone() {
        return exercisesDone;
    }

    public void setExercisesDone(Set<ExerciseDone>  exercisesDone) {
        this.exercisesDone = exercisesDone;
    }

    public void addExerciseDone(ExerciseDone exerciseDone) {
        exercisesDone.add(exerciseDone);
        exerciseDone.setWorkout(this);
    }

    public void removeExerciseDone(ExerciseDone ed) {
        exercisesDone.remove(ed);
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }
}
