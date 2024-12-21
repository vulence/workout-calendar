package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

/**
 * Represents a completed gym workout.
 *
 * A completed workout has an id, a workout id, and a date when it was completed.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Getter
@Setter
public class CompletedWorkout {

    /**
     * A unique identifier of the completed workout
     */
    @Id
    private Integer id;

    /**
     * The id of the workout which was completed
     */
    private Integer workoutId;

    /**
     * The date when the workout was completed
     */
    @JsonFormat(pattern="yyyy-MM-dd") private LocalDate date;
}
