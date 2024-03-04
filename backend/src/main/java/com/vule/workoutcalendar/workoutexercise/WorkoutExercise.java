package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class WorkoutExercise {

    @Id private Integer id;
    @Transient private String exerciseName;
    private Integer weight;
    private Integer sets;
    private Integer reps;
    private boolean completed;

    private Integer workoutId;
    private Integer exerciseId;

    public WorkoutExercise() {}

    public static WorkoutExercise from(WorkoutExerciseDto workoutExerciseDto) {
        WorkoutExercise we = new WorkoutExercise();
        we.setWeight(workoutExerciseDto.getWeight());
        we.setSets(workoutExerciseDto.getSets());
        we.setReps(workoutExerciseDto.getReps());

        return we;
    }
}
