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

export async function fetchMuscleGroups() {
    const response = await fetch('http://localhost:8080/muscleGroups');
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