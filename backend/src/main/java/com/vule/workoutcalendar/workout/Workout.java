package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Getter
@Setter
public class Workout {

    @Id private Integer id;
    private String title;
    @JsonFormat(pattern="dd-MM-yyyy") private LocalDate date;
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
}