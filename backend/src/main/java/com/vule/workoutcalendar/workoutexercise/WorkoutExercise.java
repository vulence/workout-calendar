package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Objects;

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
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        return we;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkoutExercise that = (WorkoutExercise) o;
        return completed == that.completed && Objects.equals(weight, that.weight) && Objects.equals(sets, that.sets) && Objects.equals(reps, that.reps) && Objects.equals(workoutId, that.workoutId) && Objects.equals(exerciseId, that.exerciseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, sets, reps, completed, workoutId, exerciseId);
    }

    public void setWeight(Integer weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive integer");
        }

        this.weight = weight;
    }

    public void setSets(Integer sets) {
        if (sets < 0) {
            throw new IllegalArgumentException("Sets must be a positive integer");
        }

        this.sets = sets;
    }

    public void setReps(Integer reps) {
        if (reps < 0) {
            throw new IllegalArgumentException("Reps must be a positive integer");
        }

        this.reps = reps;
    }
}
