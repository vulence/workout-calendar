package com.vule.workoutcalendar.workoutexercise.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutExerciseDtoTest {

    private WorkoutExerciseDto workoutExerciseDto;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        workoutExerciseDto = new WorkoutExerciseDto();
        validator = factory.getValidator();
    }

    @AfterEach
    void tearDown() {
        workoutExerciseDto = null;
        validator = null;
    }

    @Test
    void testValidWorkoutExerciseDto() {
        workoutExerciseDto = new WorkoutExerciseDto();
        workoutExerciseDto.setExerciseId(1);
        workoutExerciseDto.setWeight(100);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(10);

        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNullExerciseId() {
        workoutExerciseDto.setWeight(100);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setExerciseId(null);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeExerciseId() {
        workoutExerciseDto.setWeight(100);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setExerciseId(-1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("Exercise ID has to be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    void testNullWeight() {
        workoutExerciseDto.setWeight(null);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setExerciseId(1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testNullSets() {
        workoutExerciseDto.setWeight(120);
        workoutExerciseDto.setSets(null);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setExerciseId(1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeSets() {
        workoutExerciseDto.setWeight(120);
        workoutExerciseDto.setSets(-2);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setExerciseId(1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("Sets have to be a positive number", violations.iterator().next().getMessage());
    }

    @Test
    void testNulLReps() {
        workoutExerciseDto.setWeight(120);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(null);
        workoutExerciseDto.setExerciseId(1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void testNegativeReps() {
        workoutExerciseDto.setWeight(120);
        workoutExerciseDto.setSets(5);
        workoutExerciseDto.setReps(-3);
        workoutExerciseDto.setExerciseId(1);
        Set<ConstraintViolation<WorkoutExerciseDto>> violations = validator.validate(workoutExerciseDto);
        assertEquals(1, violations.size());
        assertEquals("Reps have to be a positive number", violations.iterator().next().getMessage());
    }
}