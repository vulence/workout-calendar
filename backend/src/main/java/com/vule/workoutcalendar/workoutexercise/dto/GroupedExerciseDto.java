package com.vule.workoutcalendar.workoutexercise.dto;

import java.util.List;
import java.util.Map;

public record GroupedExerciseDto(
        String exercise,
        Integer exerciseId,
        List<Map<String, Integer>> details
) {
}