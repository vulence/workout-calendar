package com.vule.workoutcalendar.exercisemusclegroup;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseMuscleGroup {

    @Id private Integer id;
    private boolean primary;

    private Integer exerciseId;
    private Integer muscleGroupId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseMuscleGroup that = (ExerciseMuscleGroup) o;
        return primary == that.primary && Objects.equals(id, that.id) && Objects.equals(exerciseId, that.exerciseId) && Objects.equals(muscleGroupId, that.muscleGroupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, primary, exerciseId, muscleGroupId);
    }
}