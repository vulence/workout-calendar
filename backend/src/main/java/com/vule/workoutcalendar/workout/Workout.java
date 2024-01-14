package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Workout {
    @Id
    private Integer id;
    private String title;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
    private String notes;
    private Integer duration;
    private Integer rating;
    private Integer userId;
    private Set<WorkoutExercise> workoutExercises = new HashSet<>();

    public Workout(String title, LocalDate date, String notes, Integer duration, Integer rating) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public Set<WorkoutExercise> getWorkoutExercises() {
        return workoutExercises;
    }

    public void setWorkoutExercises(Set<WorkoutExercise> WorkoutExercises) {
        this.workoutExercises = WorkoutExercises;
    }

    public void addWorkoutExercise(WorkoutExercise workoutExercise) {
        workoutExercises.add(workoutExercise);
        workoutExercise.setWorkout(this);
    }

    public void removeWorkoutExercise(WorkoutExercise we) {
        workoutExercises.remove(we);
    }

    public Integer getuserId() {
        return userId;
    }

    public void setuserId(Integer userId) {
        this.userId = userId;
    }
}
