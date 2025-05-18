package com.vule.workoutcalendar.workoutexercise;

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

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(Integer workoutId1, Integer exerciseId1,
                    Integer workoutId2, Integer exerciseId2, Boolean eq) {
        workoutExercise.setWorkoutId(workoutId1);
        workoutExercise.setExerciseId(exerciseId1);

        WorkoutExercise workoutExercise2 = new WorkoutExercise();
        workoutExercise2.setWorkoutId(workoutId2);
        workoutExercise2.setExerciseId(exerciseId2);

        assertEquals(eq, workoutExercise.equals(workoutExercise2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of(1, 1, 1, 1, true),
                Arguments.of(1, 1, 1, 2, false)
        );
    }
}