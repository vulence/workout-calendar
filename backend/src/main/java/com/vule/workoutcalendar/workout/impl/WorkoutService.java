package com.vule.workoutcalendar.workout.impl;

import com.vule.workoutcalendar.exerciseset.ExerciseSet;
import com.vule.workoutcalendar.exerciseset.api.ExerciseSetServiceApi;
import com.vule.workoutcalendar.workout.CompletedWorkout;
import com.vule.workoutcalendar.workout.Workout;
import com.vule.workoutcalendar.workout.api.WorkoutServiceApi;
import com.vule.workoutcalendar.workoutexercise.WorkoutExercise;
import com.vule.workoutcalendar.workoutexercise.api.WorkoutExerciseServiceApi;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WorkoutService implements WorkoutServiceApi {

    /**
     * A workout repository that communicates with the DB for CRUD operations, with Spring Data JDBC as an implementation.
     */
    private final WorkoutRepository repository;

    private final CompletedWorkoutRepository completedWorkouts;

    private final WorkoutExerciseServiceApi workoutExerciseServiceApi;

    private final ExerciseSetServiceApi exerciseSetServiceApi;

    public WorkoutService(WorkoutRepository repository,
                          CompletedWorkoutRepository completedWorkouts,
                          WorkoutExerciseServiceApi workoutExerciseServiceApi,
                          ExerciseSetServiceApi exerciseSetServiceApi) {
        this.repository = repository;
        this.completedWorkouts = completedWorkouts;
        this.workoutExerciseServiceApi = workoutExerciseServiceApi;
        this.exerciseSetServiceApi = exerciseSetServiceApi;
    }

    @Override
    public List<Workout> findAllPaged(Integer userId, Integer page, Integer size, String direction) {
        if (page <= 0) return repository.findByUserId(userId, PageRequest.of(0, Integer.MAX_VALUE));
        else return repository.findByUserId(userId, PageRequest.of(page - 1, size));
    }

    @Override
    public Map<String, List<ExerciseSet>> findWorkoutDetails(Integer userId, Integer workoutId) {
        checkWorkoutBelongsToUser(userId, workoutId);

        List<WorkoutExercise> workoutExercises = workoutExerciseServiceApi.getWorkoutExercises(workoutId);

        return workoutExercises.stream()
                        .collect(Collectors.toMap(
                                WorkoutExercise::getExerciseName,
                                workoutExercise -> exerciseSetServiceApi.getExerciseSetsByWorkoutExerciseId(workoutExercise.getId())
                        ));
    }

    @Override
    public Workout findById(Integer id, Integer userId) {
        return repository.findByIdAndUserId(id, userId).orElse(null);
    }

    @Override
    public Integer getWorkoutCount(Integer userId) {
        return repository.getWorkoutCount(userId);
    }

    @Override
    public void create(Integer userId, Workout workout) {
        workout.setUserId(userId);

        repository.save(workout);
    }

    @Override
    public void update(Integer userId, Integer workoutId, Workout workout) {
        Workout dbWorkout = findById(workoutId, userId);
        dbWorkout.setDuration(workout.getDuration());
        dbWorkout.setNotes(workout.getNotes());
        dbWorkout.setRating(workout.getRating());

        repository.save(dbWorkout);
    }

    @Override
    public void delete(Integer userId, Integer id) {
        repository.deleteByIdAndUserId(id, userId);
    }

    @Override
    public void createCompletedWorkout(Integer userId, CompletedWorkout completedWorkout) {
        completedWorkouts.save(completedWorkout);
    }

    @Override
    public List<CompletedWorkout> findAllCompletedWorkouts(Integer userId) {
        return completedWorkouts.findAllByUserId(userId);
    }

    /**
     * Checks whether the workout with id workoutId belongs to the user with id userId.
     *
     * @param userId Id of the user
     * @param workoutId Id of the workout
     *
     * @throws ResponseStatusException If the userId does not match for the workoutId
     */
    @Override
    public void checkWorkoutBelongsToUser(Integer userId, Integer workoutId) {
        if (repository.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");
    }
}
