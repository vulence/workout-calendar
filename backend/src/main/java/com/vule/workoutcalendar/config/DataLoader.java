package com.vule.workoutcalendar.config;

import com.vule.workoutcalendar.model.Exercise;
import com.vule.workoutcalendar.model.ExerciseDone;
import com.vule.workoutcalendar.model.MuscleGroup;
import com.vule.workoutcalendar.model.Workout;
import com.vule.workoutcalendar.repository.ExerciseRepository;
import com.vule.workoutcalendar.repository.MuscleGroupRepository;
import com.vule.workoutcalendar.repository.WorkoutRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
public class DataLoader implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final MuscleGroupRepository muscleGroupRepository;

    public DataLoader(ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository, MuscleGroupRepository muscleGroupRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutRepository = workoutRepository;
        this.muscleGroupRepository = muscleGroupRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        MuscleGroup chest = muscleGroupRepository.save(new MuscleGroup("Chest", "Large, flat muscle on the back that stretches to the sides"));

        Exercise bench = new Exercise("Bench press", "A compound strength training exercise that primarily targets several muscle groups in the upper body, mainly the chest and triceps.");
        bench.addMuscleGroup(chest, chest.getName());

        MuscleGroup triceps = muscleGroupRepository.findById(5).get();
        bench.addMuscleGroup(triceps, triceps.getName());

        exerciseRepository.save(bench);

        AggregateReference<Exercise, Integer> exercise = AggregateReference.to(7);

        Workout w = new Workout(LocalDateTime.now(), "nema notes", 120, null);
        w.addExerciseDone(new ExerciseDone(120, 2, 4, exercise));

        workoutRepository.save(w);
    }
}