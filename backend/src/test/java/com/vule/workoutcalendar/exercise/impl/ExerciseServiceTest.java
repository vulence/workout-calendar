package com.vule.workoutcalendar.exercise.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExerciseServiceTest {

    @MockBean
    private ExerciseRepository exercises;

    @Autowired
    private ExerciseService exerciseService;

    @Test
    void testFindAll() {
        Exercise exercise1 = new Exercise("Bench press", "Compound chest exercise");
        Exercise exercise2 = new Exercise("Squat", "Compound leg exercise");
        List<Exercise> exerciseList = Arrays.asList(exercise1, exercise2);

        when(exercises.findAll()).thenReturn(exerciseList);

        List<Exercise> result = exerciseService.findAll();
        assertEquals(2, result.size());
        assertEquals(exercise1, result.get(0));
        assertEquals(exercise2, result.get(1));
        verify(exercises, times(1)).findAll();
    }

    @Test
    void testFindByIdIfItExists() {
        Integer exerciseId = 1;
        Exercise exercise = new Exercise("Bench press", "Compound chest exercise");

        when(exercises.findById(exerciseId)).thenReturn(Optional.of(exercise));

        Exercise result = exerciseService.findById(exerciseId);

        assertNotNull(result);
        assertEquals(exercise, result);
        verify(exercises, times(1)).findById(exerciseId);
    }

    @Test
    void testFindByIdIfItDoesNotExist() {
        when(exercises.findById(any())).thenReturn(Optional.empty());
        Exercise result = exerciseService.findById(any());
        assertNull(result);
    }

    @Test
    void testCreateNewExercise() {
        Exercise exercise = new Exercise("Bench press", "Compound chest exercise");
        exerciseService.create(exercise);

        verify(exercises, times(1)).save(exercise);
    }

    @Test
    void testDeleteExercise() {
        exerciseService.delete(1);
        verify(exercises, times(1)).deleteById(1);
    }
}