package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class Workout {

    @Id private Integer id;
    private String title;
    @JsonFormat(pattern="yyyy-MM-dd") private LocalDate date;
    private String notes;
    @Positive private Integer duration;
    @Min(0) @Max(5) private Integer rating;
    private Integer userId;

    public Workout(String title, LocalDate date, String notes, Integer duration, Integer rating) {
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.duration = duration;
        this.rating = rating;
    }

    public void setDuration(Integer duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        this.duration = duration;
    }

    public void setRating(Integer rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id) && Objects.equals(title.toLowerCase(), workout.title.toLowerCase()) && Objects.equals(date, workout.date) && Objects.equals(notes.toLowerCase(), workout.notes.toLowerCase()) && Objects.equals(duration, workout.duration) && Objects.equals(rating, workout.rating) && Objects.equals(userId, workout.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, notes, duration, rating, userId);
    }
}