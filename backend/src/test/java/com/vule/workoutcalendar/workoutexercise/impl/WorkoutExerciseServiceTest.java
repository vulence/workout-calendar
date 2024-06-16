package com.vule.workoutcalendar.workoutexercise.impl;

import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.impl.WorkoutRepository;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.dto.GroupedExerciseDto;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
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
        we1.setSets(5);
        we1.setWeight(120);
        we1.setReps(10);
        we1.setExerciseId(1);
        we1.setWorkoutId(1);

        WorkoutExercise we2 = new WorkoutExercise();
        we2.setSets(3);
        we2.setWeight(150);
        we2.setReps(5);
        we2.setExerciseId(2);
        we2.setWorkoutId(1);

        List<WorkoutExercise> workoutExercises = List.of(we1, we2);

        when(workoutExerciseRepository.findAllByWorkoutId(1)).thenReturn(Optional.of(workoutExercises));
        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));

        List<WorkoutExercise> result = workoutExerciseService.getWorkoutExercises(1, 1);
        assertEquals(2, result.size());
        assertEquals(we1, result.get(0));
        assertEquals(we2, result.get(1));
    }

    @Test
    public void getGroupedWorkoutExercisesSuccessfully() {
        WorkoutExercise we1 = new WorkoutExercise();
        we1.setWeight(120);
        we1.setSets(5);
        we1.setReps(10);
        we1.setExerciseId(1);
        we1.setId(1);

        WorkoutExercise we2 = new WorkoutExercise();
        we2.setWeight(80);
        we2.setSets(3);
        we2.setReps(5);
        we2.setExerciseId(1);
        we2.setId(2);

        when(workoutExerciseRepository.findAllByWorkoutId(any())).thenReturn(Optional.of(List.of(we1, we2)));
        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));
        when(exerciseRepository.findExerciseName(any())).thenReturn("Bench press");

        List<GroupedExerciseDto> groupedExercisesDto = new ArrayList<>();

        groupedExercisesDto.add(new GroupedExerciseDto("Bench press", List.of(
                Map.of("weight", we1.getWeight(), "sets", we1.getSets(), "reps", we1.getReps(), "id", we1.getId()),
                Map.of("weight", we2.getWeight(), "sets", we2.getSets(), "reps", we2.getReps(), "id", we2.getId())
        )));

        List<GroupedExerciseDto> result = workoutExerciseService.getGroupedWorkoutExercises(1, 1);
        assertEquals(groupedExercisesDto.size(), result.size());
        assertEquals(groupedExercisesDto.getFirst(), result.getFirst());
    }

    @Test
    public void addWorkoutExerciseSuccessfully() {
        WorkoutExerciseDto workoutExerciseDto = new WorkoutExerciseDto();
        workoutExerciseDto.setExerciseId(1);
        workoutExerciseDto.setWeight(120);
        workoutExerciseDto.setReps(10);
        workoutExerciseDto.setSets(5);

        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));
        workoutExerciseService.addWorkoutExercise(1, 1, workoutExerciseDto);

        WorkoutExercise we = WorkoutExercise.from(workoutExerciseDto);
        we.setWorkoutId(1);
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        verify(workoutExerciseRepository, times(1)).save(we);
    }

    @Test
    public void updateWorkoutExerciseSuccessfully() {
        WorkoutExercise we1 = new WorkoutExercise();
        we1.setWorkoutId(1);
        we1.setExerciseId(1);
        we1.setSets(5);
        we1.setWeight(120);
        we1.setReps(10);

        when(workoutExerciseRepository.findById(any())).thenReturn(Optional.of(we1));

        WorkoutExercise we2 = new WorkoutExercise();
        we2.setWeight(100);
        we2.setSets(3);
        we2.setReps(5);

        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));

        workoutExerciseService.updateWorkoutExercise(1, 1, we2);
        verify(workoutExerciseRepository, times(1)).save(we1);
        assertEquals(we1.getWeight(), 100);
        assertEquals(we1.getSets(), 3);
        assertEquals(we1.getReps(), 5);
    }

    @Test
    public void deleteWorkoutExerciseSuccessfully() {
        when(workoutRepository.findByIdAndUserId(any(), any())).thenReturn(Optional.of(new Workout()));

        workoutExerciseService.deleteWorkoutExercise(1, 1, 1);
        verify(workoutExerciseRepository, times(1)).delete(any());
    }
}