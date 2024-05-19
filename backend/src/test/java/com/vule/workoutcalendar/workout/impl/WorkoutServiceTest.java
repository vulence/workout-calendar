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
    public void testFindById() {
        Integer userId = 1;
        Integer workoutId = 1;

        Workout workout = new Workout("Workout1", LocalDate.now(), "Notes1", 30, 4);

        when(workouts.findByIdAndUserId(workoutId, userId)).thenReturn(Optional.of(workout));

        Workout result = workoutService.findById(workoutId, userId);

        assertNotNull(result);
        assertEquals(workout, result);
        verify(workouts, times(1)).findByIdAndUserId(workoutId, userId);
    }
}