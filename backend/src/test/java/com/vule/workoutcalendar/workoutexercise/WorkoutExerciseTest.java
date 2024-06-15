package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

class WorkoutExerciseTest {
    private WorkoutExercise workoutExercise;

    @BeforeEach
    void setUp() {
        workoutExercise = new WorkoutExercise();
    }

    @AfterEach
    void tearDown() {
        workoutExercise = null;
    }

    @Test
    void testValidWorkoutExercise() {
        workoutExercise.setWeight(50);
        workoutExercise.setSets(2);
        workoutExercise.setReps(5);

        assertEquals(50, workoutExercise.getWeight());
        assertEquals(2, workoutExercise.getSets());
        assertEquals(5, workoutExercise.getReps());
    }

    @Test
    void testNegativeWeightSetter() {
        assertThrows(IllegalArgumentException.class, () -> workoutExercise.setWeight(-1));
    }

    @Test
    void testNegativeSetsSetter() {
        assertThrows(IllegalArgumentException.class, () -> workoutExercise.setSets(-1));
    }

    @Test
    void testNegativeRepsSetter() {
        assertThrows(IllegalArgumentException.class, () -> workoutExercise.setReps(-1));
    }

    @Test
    void testFromWorkoutExerciseDto() {
        WorkoutExerciseDto workoutExerciseDto = new WorkoutExerciseDto();
        workoutExerciseDto.setWeight(50);
        workoutExerciseDto.setSets(2);
        workoutExerciseDto.setReps(5);
        workoutExerciseDto.setExerciseId(1);

        workoutExercise = WorkoutExercise.from(workoutExerciseDto);

        assertEquals(workoutExerciseDto.getWeight(), workoutExercise.getWeight());
        assertEquals(workoutExerciseDto.getSets(), workoutExercise.getSets());
        assertEquals(workoutExerciseDto.getReps(), workoutExercise.getReps());
        assertEquals(workoutExerciseDto.getExerciseId(), workoutExercise.getExerciseId());
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(Integer weight1, Integer sets1, Integer reps1, Boolean completed1, Integer workoutId1, Integer exerciseId1,
                    Integer weight2, Integer sets2, Integer reps2, Boolean completed2, Integer workoutId2, Integer exerciseId2, Boolean eq) {
        workoutExercise.setWeight(weight1);
        workoutExercise.setSets(sets1);
        workoutExercise.setReps(reps1);
        workoutExercise.setCompleted(completed1);
        workoutExercise.setWorkoutId(workoutId1);
        workoutExercise.setExerciseId(exerciseId1);

        WorkoutExercise workoutExercise2 = new WorkoutExercise();
        workoutExercise2.setWeight(weight2);
        workoutExercise2.setSets(sets2);
        workoutExercise2.setReps(reps2);
        workoutExercise2.setCompleted(completed2);
        workoutExercise2.setWorkoutId(workoutId2);
        workoutExercise2.setExerciseId(exerciseId2);

        assertEquals(eq, workoutExercise.equals(workoutExercise2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of(120, 5, 10, true, 1, 1, 120, 5, 10, true, 1, 1, true),
                Arguments.of(120, 5, 10, true, 1, 1, 120, 5, 10, true, 2, 1, false)
        );
    }
}