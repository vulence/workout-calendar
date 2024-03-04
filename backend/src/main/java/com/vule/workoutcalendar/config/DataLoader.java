package com.vule.workoutcalendar.config;

import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.WorkoutRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner
{
    private final WorkoutRepository workouts;

    public DataLoader(WorkoutRepository workouts) {
        this.workouts = workouts;
    }

    @Override
    public void run(String... args) throws Exception {
        if (workouts.count() == 0) {
            for (int i = 1; i < 29; i++) {
                Workout w = new Workout("Chest day", LocalDate.of(2024, LocalDate.now().getMonth(), i), "nema notes", 60, 3);
                w.setUserId(5);
                workouts.save(w);
            }
        }
    }
}
