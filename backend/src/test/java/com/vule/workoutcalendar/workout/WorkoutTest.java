package com.vule.workoutcalendar.workout;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutTest {

    private Workout workout;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        validator = null;
        workout = null;
    }

    @Test
    void testValidWorkout() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", 60, 5);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNegativeDuration() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", -1, 5);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertEquals(1, violations.size());
        assertEquals("must be greater than 0", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeDurationSetter() {
        workout = new Workout();
        assertThrows(IllegalArgumentException.class, () -> workout.setDuration(-1));
    }

    @Test
    void testNegativeRating() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", 120, -1);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertEquals(1, violations.size());
        assertEquals("must be greater than or equal to 0", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeRatingSetter() {
        workout = new Workout();
        assertThrows(IllegalArgumentException.class, () -> workout.setRating(-1));
    }

    @Test
    void testPositiveRating() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", 60, 10);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertEquals(1, violations.size());
        assertEquals("must be less than or equal to 5", violations.iterator().next().getMessage());
    }

    @Test
    void testPositiveRatingSetter() {
        workout = new Workout();
        assertThrows(IllegalArgumentException.class, () -> workout.setRating(10));
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(String title1, LocalDate date1, String notes1, Integer duration1, Integer rating1,
                    String title2, LocalDate date2, String notes2, Integer duration2, Integer rating2, boolean eq) {
        workout = new Workout(title1, date1, notes1, duration1, rating1);

        Workout w2 = new Workout(title2, date2, notes2, duration2, rating2);

        assertEquals(eq, workout.equals(w2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
          Arguments.of("Chest day", LocalDate.now(), "No notes", 60, 5, "chest day", LocalDate.now(), "no notes", 60, 5, true),
                Arguments.of("Chest day", LocalDate.now(), "No notes", 120, 3, "Back day", LocalDate.now(), "Some notes", 30, 1, false)
        );
    }
}