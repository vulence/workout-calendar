/*
* START WORKOUTS
*/

export async function fetchWorkouts() {
    const response = await fetch("http://localhost:8080/workouts", {
        method: "GET",
        credentials: 'include',
    });

    const result = await response.json();
    return result;
};

export async function fetchWorkoutById(workoutId: string) {
    const response = await fetch(`http://localhost:8080/workouts/${workoutId}`, {
        method: "GET",
        credentials: 'include',
    });

    const result = await response.json();
    return result;
};

export async function fetchWorkoutExercises(workoutId: string) {
    const response = await fetch(`http://localhost:8080/workouts/${workoutId}/exercises`, {
        method: "GET",
        credentials: "include",
    });

    const result = await response.json();
    return result;
};

export async function updateExerciseCompleted(workoutId : string, exerciseDoneId: string, completed: boolean) {
    const response = await fetch(`http://localhost:8080/workouts/${workoutId}/setCompleted`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: "include",
        body: JSON.stringify({ exerciseDoneId, completed })
    });

    return response.status;
};

/*
* END WORKOUTS
*/

/*
* START EXERCISES
*/

export async function fetchExercises() {
    const response = await fetch("http://localhost:8080/exercises");
    const result = await response.json();
    return result;
};

export async function fetchExerciseById(exerciseId: string) {
    const response = await fetch(`http://localhost:8080/exercises/${exerciseId}`);
    const result = await response.json();
    return result;
};

export async function fetchMaxWeights(exerciseId: string) {
    const response = await fetch(`http://localhost:8080/exercises/${exerciseId}/workouts`, {
        method: "GET",
        credentials: "include",
    });

    const result = await response.json();
    return result;
};

export async function fetchExerciseHistory(exerciseId: string) {
    const response = await fetch(`http://localhost:8080/exercises/${exerciseId}/exerciseHistory`, {
        method: "GET",
        credentials: "include",
    });

    const result = await response.json();
    return result;
};

/*
* END EXERCISES
*/

/*
* START MUSCLE GROUPS
*/

export async function fetchMuscleGroups() {
    const response = await fetch('http://localhost:8080/muscleGroups');
    const result = await response.json();
    return result;
};

/*
* END MUSCLE GROUPS
*/