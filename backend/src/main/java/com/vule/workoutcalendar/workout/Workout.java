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

/**
 * Represents a gym workout
 *
 * A workout has an id, a title, a date when it was completed, notes, a (positive) duration in minutes,
 * a (0-5) rating, and a userId of the user who did the workout.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class Workout {

    /**
     * A unique identifier of the workout
     */
    @Id private Integer id;

    /**
     * A title of the workout (Chest day, Leg day..)
     */
    private String title;

    /**
     * The date when the workout was completed
     */
    @JsonFormat(pattern="yyyy-MM-dd") private LocalDate date;

    /**
     * Notes specific to that workout
     */
    private String notes;

    /**
     * Duration of the workout (in minutes)
     */
    @Positive private Integer duration;

    /**
     * The rating that represents user's satisfaction with the workout
     */
    @Min(0) @Max(5) private Integer rating;

    /**
     * The id of the user who completed the workout
     */
    private Integer userId;

    /**
     * Creates a new Workout and sets title, date, notes, duration and rating to the input values
     *
     * @param title workout title as a string
     * @param date workout date as a LocalDate
     * @param notes workout notes as a string
     * @param duration workout duration as an integer
     * @param rating workout rating as an integer
     */
    public Workout(String title, LocalDate date, String notes, Integer duration, Integer rating) {
        this.title = title;
        this.date = date;
        this.notes = notes;
        this.duration = duration;
        this.rating = rating;
    }

    /**
     * Sets the duration of the workout to the input value
     *
     * The inputted duration cannot be less than 0
     *
     * @param duration workout duration as an integer
     *
     * @throws IllegalArgumentException if the entered duration is less than 0
     */
    public void setDuration(Integer duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }

        this.duration = duration;
    }

    /**
     * Sets the rating of the workout to the input value
     *
     * The inputted rating cannot be less than 0 or more than 5
     *
     * @param rating workout rating as an integer
     *
     * @throws IllegalArgumentException if the entered rating is less than 0 or more than 5
     */
    public void setRating(Integer rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        this.rating = rating;
    }

    /**
     * Compares two workouts according to their ids, titles (case-insensitive), dates, notes, durations, ratings, and userIds
     *
     * @param o the second workout to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the Workout class,
     *     and have the same ids, titles, dates, notes, durations, ratings and userIds</li>
     *     <li><b>false</b> - if objects don't belong to the Workout class, if the inputted object is null
     *     or if they don't have the same ids, titles, dates, notes, durations, ratings or userIds</li>
     * </ul>
     */
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