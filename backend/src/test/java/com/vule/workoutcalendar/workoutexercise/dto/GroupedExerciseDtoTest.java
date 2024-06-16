package com.vule.workoutcalendar.workoutexercise.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroupedExerciseDtoTest {

    @Test
    public void testValidGroupedExercise() {
        String exercise = "Bench press";
        List<Map<String, Integer>> details = new ArrayList<>();

        details.add(Map.of("weight", 120, "sets", 5, "reps", 3));
        details.add(Map.of("weight", 130, "sets", 3, "reps", 2));
        details.add(Map.of("weight", 100, "sets", 4, "reps", 10));

        GroupedExerciseDto groupedExerciseDto = new GroupedExerciseDto(exercise, details);

        assertEquals(groupedExerciseDto.exercise(), exercise);
        assertEquals(groupedExerciseDto.details(), details);
    }
}