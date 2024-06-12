package com.vule.workoutcalendar.musclegroup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MuscleGroupTest {

    private MuscleGroup muscleGroup;

    @BeforeEach
    void setUp() {
        muscleGroup = new MuscleGroup();
    }

    @AfterEach
    void tearDown() {
        muscleGroup = null;
    }

    @Test
    void testValidMuscleGroup() {
        muscleGroup = new MuscleGroup("Chest", "Pectoralis major and pectoralis minor");
        assertEquals(muscleGroup.getName(), "Chest");
        assertEquals(muscleGroup.getDescription(), "Pectoralis major and pectoralis minor");
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(String name1, String description1, String name2, String description2, boolean eq) {
        muscleGroup.setName(name1);
        muscleGroup.setDescription(description1);

        MuscleGroup mg2 = new MuscleGroup(name2, description2);

        assertEquals(eq, muscleGroup.equals(mg2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of("Chest", "Pectoralis major and pectoralis minor", "chest", "none", true),
                Arguments.of("Biceps", "Big arms", "Triceps", "Pushing muscle", false)
        );
    }
}