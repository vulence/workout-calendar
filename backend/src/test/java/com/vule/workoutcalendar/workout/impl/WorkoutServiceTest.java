package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.workout.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkoutServiceTest {

    @MockBean
    private WorkoutRepository workouts;

    @Autowired
    private WorkoutService workoutService;

    @Test
    public void testFindAllPaged() {
        Integer userId = 1;
        Integer page = 1;
        Integer size = 12;
        String direction = "DESC";

        Workout workout1 = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);
        Workout workout2 = new Workout("Workout2", LocalDate.now(), "Notes2", 45, 5);
        List<Workout> workoutList = Arrays.asList(workout1, workout2);

        when(workouts.findByUserId(eq(userId), any(PageRequest.class))).thenReturn(workoutList);

        List<Workout> result = workoutService.findAllPaged(userId, page, size, direction);
        assertEquals(2, result.size());
        assertEquals(workout1, result.get(0));
        assertEquals(workout2, result.get(1));
        verify(workouts, times(1)).findByUserId(eq(userId), any(PageRequest.class));
    }

    @Test
    public void testFindByIdIfItExists() {
        Integer userId = 1;
        Integer workoutId = 1;

        Workout workout = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);

        when(workouts.findByIdAndUserId(workoutId, userId)).thenReturn(Optional.of(workout));

        Workout result = workoutService.findById(workoutId, userId);

        assertNotNull(result);
        assertEquals(workout, result);
        verify(workouts, times(1)).findByIdAndUserId(workoutId, userId);
    }

    @Test
    public void testFindByIdIfItDoesNotExist() {
        when(workouts.findByIdAndUserId(any(), anyInt())).thenReturn(Optional.empty());
        Workout result = workoutService.findById(any(), anyInt());
        assertNull(result);
    }

    @Test
    public void testFindTodaysWorkoutIfItExists() {
        Workout workout1 = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);
        Workout workout2 = new Workout("Workout2", LocalDate.of(2023, 5, 4), "Notes2", 45, 5);

        when(workouts.findTodaysWorkout(1, LocalDate.now())).thenReturn(Optional.of(workout1));

        Workout result = workoutService.findTodaysWorkout(1);
        assertNotNull(result);
        assertEquals(workout1, result);
        verify(workouts, times(1)).findTodaysWorkout(1, LocalDate.now());
    }

    @Test
    public void testFindTodaysWorkoutWhenItDoesntExist() {
        when(workouts.findTodaysWorkout(any(), any())).thenReturn(Optional.empty());
        Workout result = workoutService.findTodaysWorkout(1);
        assertNull(result);
    }

    @Test
    public void testWorkoutCount() {
        Integer userId = 1;
        Workout workout1 = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);
        Workout workout2 = new Workout("Workout2", LocalDate.now(), "Notes2", 45, 5);
        workout1.setUserId(userId);
        workout2.setUserId(userId);

        when(workouts.getWorkoutCount(userId)).thenReturn(2);

        Integer count = workoutService.getWorkoutCount(userId);
        assertEquals(2, count);
    }

    @Test
    public void testCreateNewWorkout() {
        Workout workout = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);
        workoutService.create(1, workout);

        verify(workouts, times(1)).save(workout);
    }

    @Test
    public void testUpdateWorkout() {
        Workout workout = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);
        workout.setUserId(1);

        when(workouts.findByIdAndUserId(1, 1)).thenReturn(Optional.of(workout));

        Workout updatedWorkout = new Workout("UpdatedWorkout", LocalDate.now(), "Updated notes", 120, 2);
        workoutService.update(1, 1, updatedWorkout);

        verify(workouts, times(1)).save(workout);
        assertEquals("Updated notes", workout.getNotes());
        assertEquals(120, workout.getDuration());
        assertEquals(2, workout.getRating());
    }

    @Test
    public void testDeleteWorkout() {
        workoutService.delete(1, 1);
        verify(workouts, times(1)).deleteByIdAndUserId(1, 1);
    }
}