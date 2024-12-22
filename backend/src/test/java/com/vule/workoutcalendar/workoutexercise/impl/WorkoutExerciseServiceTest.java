package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.impl.WorkoutRepository;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkoutExerciseServiceTest {

    @MockBean
    private WorkoutExerciseRepository workoutExerciseRepository;

    @MockBean
    private WorkoutRepository workoutRepository;

    @MockBean
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutExerciseService workoutExerciseService;

    @Test
    public void testGetWorkoutExercisesForWorkoutSuccessfully() {
        WorkoutExercise we1 = new WorkoutExercise();
        we1.setExerciseId(1);
        we1.setWorkoutId(1);

        WorkoutExercise we2 = new WorkoutExercise();
        we2.setExerciseId(2);
        we2.setWorkoutId(1);

        List<WorkoutExercise> workoutExercises = List.of(we1, we2);

        when(workoutExerciseRepository.findAllByWorkoutId(1)).thenReturn(Optional.of(workoutExercises));
        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));

        List<WorkoutExercise> result = workoutExerciseService.getWorkoutExercises(1);
        assertEquals(2, result.size());
        assertEquals(we1, result.get(0));
        assertEquals(we2, result.get(1));
    }

    @Test
    public void addWorkoutExerciseSuccessfully() {
        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setExerciseId(1);
        workoutExercise.setWorkoutId(1);

        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));
        workoutExerciseService.addWorkoutExercise(workoutExercise);

        verify(workoutExerciseRepository, times(1)).save(workoutExercise);
    }

    @Test
    public void deleteWorkoutExerciseSuccessfully() {
        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));
        when(workoutExerciseRepository.findById(1)).thenReturn(Optional.of(new WorkoutExercise()));

        workoutExerciseService.deleteWorkoutExercise(1);
        verify(workoutExerciseRepository, times(1)).delete(any());
    }
}