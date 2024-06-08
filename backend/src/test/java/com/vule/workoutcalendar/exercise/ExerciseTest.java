package com.vule.workoutcalendar.exercise;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise();
    }

    @AfterEach
    void tearDown() {
        exercise = null;
    }

    @Test
    void testValidExercise() {
        exercise = new Exercise("Bench press", "Compound chest exercise");
        assertEquals(exercise.getName(), "Bench press");
        assertEquals(exercise.getDescription(), "Compound chest exercise");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(String name1, String description1, String name2, String description2, boolean eq) {
        exercise.setName(name1);
        exercise.setDescription(description1);

        Exercise e2 = new Exercise(name2, description2);

        assertEquals(eq, exercise.equals(e2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of("Bench press", "Compound chest exercise", "benchpress", "none", true),
                Arguments.of("Squat", "Compound leg exercise", "Benchpress", "Compound leg exercise", false)
        );
    }
}