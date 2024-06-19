package com.vule.workoutcalendar.workoutexercise.dto;

import java.util.List;
import java.util.Map;

/**
 * A DTO used to transfer data about WorkoutExercise objects, mostly used for mobile.
 *
 * @param exercise The name of the exercise
 * @param exerciseId The id of the exercise
 * @param details A list of maps, each containing the following keys:
 *                - weight - the weight with which the exercise was done
 *                - sets - the number of sets which were done for the exercise
 *                - reps - the number of reps in each set
 *                - id - the id of the workoutExercise
 */
public record GroupedExerciseDto(
        String exercise,
        Integer exerciseId,
        List<Map<String, Integer>> details
) {
}