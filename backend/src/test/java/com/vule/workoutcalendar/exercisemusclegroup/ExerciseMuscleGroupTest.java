package com.vule.workoutcalendar.exercisemusclegroup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.Stream;

class ExerciseMuscleGroupTest {
    private ExerciseMuscleGroup exerciseMuscleGroup;

    @BeforeEach
    void setUp() {
        exerciseMuscleGroup = new ExerciseMuscleGroup();
    }

    @AfterEach
    void tearDown() {
        exerciseMuscleGroup = null;
    }

    @Test
    void testValidExerciseMuscleGroup() {
        exerciseMuscleGroup.setExerciseId(1);
        exerciseMuscleGroup.setMuscleGroupId(1);
        exerciseMuscleGroup.setPrimary(false);

        assertEquals(1, exerciseMuscleGroup.getExerciseId());
        assertEquals(1, exerciseMuscleGroup.getMuscleGroupId());
        assertFalse(exerciseMuscleGroup.isPrimary());
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(Integer id1, Boolean primary1, Integer exerciseId1, Integer muscleGroupId1,
                    Integer id2, Boolean primary2, Integer exerciseId2, Integer muscleGroupId2, boolean eq) {
        exerciseMuscleGroup.setId(id1);
        exerciseMuscleGroup.setPrimary(primary1);
        exerciseMuscleGroup.setExerciseId(exerciseId1);
        exerciseMuscleGroup.setMuscleGroupId(muscleGroupId1);

        ExerciseMuscleGroup exerciseMuscleGroup2 = new ExerciseMuscleGroup();
        exerciseMuscleGroup2.setId(id2);
        exerciseMuscleGroup2.setPrimary(primary2);
        exerciseMuscleGroup2.setExerciseId(exerciseId2);
        exerciseMuscleGroup2.setMuscleGroupId(muscleGroupId2);

        assertEquals(eq, exerciseMuscleGroup.equals(exerciseMuscleGroup2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of(1, true, 1, 1, 1, true, 1, 1, true),
                Arguments.of(1, false, 1, 1, 1, true, 1, 1, false)
        );
    }
}