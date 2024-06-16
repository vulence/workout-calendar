package com.vule.workoutcalendar.exercisemusclegroup.impl;

import com.vule.workoutcalendar.exercise.Exercise;
import com.vule.workoutcalendar.exercise.impl.ExerciseRepository;
import com.vule.workoutcalendar.exercisemusclegroup.ExerciseMuscleGroup;
import com.vule.workoutcalendar.musclegroup.MuscleGroup;
import com.vule.workoutcalendar.musclegroup.impl.MuscleGroupRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ExerciseMuscleGroupServiceTest {

    @MockBean
    private ExerciseRepository exerciseRepository;

    @MockBean
    private MuscleGroupRepository muscleGroupRepository;

    @MockBean
    private ExerciseMuscleGroupRepository exerciseMuscleGroupRepository;

    @Autowired
    private ExerciseMuscleGroupService exerciseMuscleGroupService;

    @Test
    void testFindPrimaryMuscleGroupForExerciseSuccessfully() {
        Exercise exercise = new Exercise("Bench press", "Compound chest exercise");
        exercise.setId(1);
        when(exerciseRepository.findByName("Bench press")).thenReturn(exercise);

        MuscleGroup muscleGroup = new MuscleGroup("Chest", "Large frontal muscles");
        muscleGroup.setId(1);
        ExerciseMuscleGroup exerciseMuscleGroup = new ExerciseMuscleGroup();
        exerciseMuscleGroup.setId(1);
        exerciseMuscleGroup.setPrimary(true);
        exerciseMuscleGroup.setMuscleGroupId(1);
        exerciseMuscleGroup.setExerciseId(1);

        when(exerciseMuscleGroupRepository.findPrimaryMuscleGroupForExercise(1)).thenReturn(exerciseMuscleGroup);

        ExerciseMuscleGroup result = exerciseMuscleGroupService.findPrimaryMuscleGroupForExercise("Bench press");
        assertEquals(exerciseMuscleGroup, result);
    }

    @Test
    void testFindAllMuscleGroupsForExerciseSuccessfully() {
        Exercise exercise = new Exercise("Bench press", "Compound chest exercise");
        exercise.setId(1);
        when(exerciseRepository.findByName("Bench press")).thenReturn(exercise);

        MuscleGroup muscleGroup1 = new MuscleGroup("Chest", "Large frontal muscles");
        muscleGroup1.setId(1);
        MuscleGroup muscleGroup2 = new MuscleGroup("Shoulders", "Front deltoids");
        muscleGroup2.setId(2);

        ExerciseMuscleGroup exerciseMuscleGroup = new ExerciseMuscleGroup();
        exerciseMuscleGroup.setId(1);
        exerciseMuscleGroup.setPrimary(true);
        exerciseMuscleGroup.setMuscleGroupId(1);
        exerciseMuscleGroup.setExerciseId(1);
        ExerciseMuscleGroup exerciseMuscleGroup2 = new ExerciseMuscleGroup();
        exerciseMuscleGroup2.setId(2);
        exerciseMuscleGroup2.setPrimary(false);
        exerciseMuscleGroup2.setMuscleGroupId(2);
        exerciseMuscleGroup.setExerciseId(1);

        when(exerciseMuscleGroupRepository.findAllMuscleGroupsForExercise(exercise.getId())).thenReturn(List.of(exerciseMuscleGroup, exerciseMuscleGroup2));

        List<ExerciseMuscleGroup> result = exerciseMuscleGroupService.findAllMuscleGroupsForExercise("Bench press");
        assertEquals(2, result.size());
        assertEquals(exerciseMuscleGroup, result.get(0));
        assertEquals(exerciseMuscleGroup2, result.get(1));
    }

    @Test
    void testFindAllExercisesForMuscleGroupSuccessfully() {
        Exercise exercise1 = new Exercise("Bench press", "Compound chest exercise");
        exercise1.setId(1);
        Exercise exercise2 = new Exercise("Dumbbell press", "Pressing variant");
        exercise2.setId(2);

        MuscleGroup muscleGroup = new MuscleGroup("Chest", "Large frontal muscles");
        muscleGroup.setId(1);
        when(muscleGroupRepository.findByName("Chest")).thenReturn(muscleGroup);

        ExerciseMuscleGroup exerciseMuscleGroup = new ExerciseMuscleGroup();
        exerciseMuscleGroup.setId(1);
        exerciseMuscleGroup.setPrimary(true);
        exerciseMuscleGroup.setMuscleGroupId(1);
        exerciseMuscleGroup.setExerciseId(1);
        ExerciseMuscleGroup exerciseMuscleGroup2 = new ExerciseMuscleGroup();
        exerciseMuscleGroup2.setId(2);
        exerciseMuscleGroup2.setPrimary(false);
        exerciseMuscleGroup2.setMuscleGroupId(1);
        exerciseMuscleGroup.setExerciseId(2);

        when(exerciseMuscleGroupRepository.findAllExercisesForMuscleGroup(muscleGroup.getId())).thenReturn(List.of(exerciseMuscleGroup, exerciseMuscleGroup2));

        List<ExerciseMuscleGroup> result = exerciseMuscleGroupService.findAllExercisesForMuscleGroup("Chest");
        assertEquals(2, result.size());
        assertEquals(exerciseMuscleGroup, result.get(0));
        assertEquals(exerciseMuscleGroup2, result.get(1));
    }
}