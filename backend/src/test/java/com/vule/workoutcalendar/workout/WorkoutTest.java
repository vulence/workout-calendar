package com.vule.workoutcalendar.workout;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

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
    void testNegativeRating() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", 120, -1);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertEquals(1, violations.size());
        assertEquals("must be greater than or equal to 0", violations.iterator().next().getMessage());
    }

    @Test
    void testPositiveRating() {
        workout = new Workout("Chest day", LocalDate.now(), "Max intensity", 60, 10);
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        assertEquals(1, violations.size());
        assertEquals("must be less than or equal to 5", violations.iterator().next().getMessage());
    }
}