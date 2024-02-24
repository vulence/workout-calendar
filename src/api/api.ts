import Userfront from '@userfront/toolkit';
    
const API_URL = "http://localhost:8080";

/*
* START WORKOUTS
*/

export async function fetchTodaysWorkout() {
    const response = await fetch(`${API_URL}/workouts/findTodaysWorkout`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
};

export async function fetchWorkouts(page = "0") {
    const response = await fetch(`${API_URL}/workouts?page=${page}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
};

export async function fetchWorkoutById(workoutId: string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
};

export async function fetchWorkoutsCount() {
    const response = await fetch(`${API_URL}/workouts/count`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
}

export async function fetchWorkoutExercises(workoutId: string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
};

export async function submitWorkout(workout : any) {
    const response = await fetch(`${API_URL}/workouts/new`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(workout),
    });

    const result = await response.json();
    return result.message;
};

export async function submitWorkoutExercise(workoutId : string, workoutExerciseDto : any) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        credentials: 'include',
        body: JSON.stringify(workoutExerciseDto),
    });

    return response.status;
};

export async function updateWorkout(workoutId : string, objectNode : any) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(objectNode),
    });

    return response.status;
};

export async function deleteWorkout(workoutId : string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}`, {
        method: "DELETE",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    return response.status;
};

export async function deleteWorkoutExercise(workoutId : string, workoutExerciseId : string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(workoutExerciseId),
    });

    return response.status;
}

export async function updateWorkoutExercise(workoutId : string, workoutExercise : any) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(workoutExercise),
    });

    return response.status;
};

export async function updateWorkoutExerciseCompleted(workoutId : string, workoutExerciseId: string, completed: boolean) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/setCompleted`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify({ workoutExerciseId, completed })
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
    const response = await fetch(`${API_URL}/exercises`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });
    
    const result = await response.json();
    return result;
};

export async function fetchExercisesByMuscleGroup(muscleGroupId: string) {
    const response = await fetch(`${API_URL}/exercises/muscleGroups/${muscleGroupId}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`,
        }
    });

    const result = await response.json();
    return result;
}

export async function fetchExerciseById(exerciseId: string) {
    const response = await fetch(`${API_URL}/exercises/${exerciseId}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });
    const result = await response.json();
    return result;
};

export async function fetchMaxWeights(exerciseId: string) {
    const response = await fetch(`${API_URL}/exercises/${exerciseId}/workouts`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });

    const result = await response.json();
    return result;
};

export async function fetchExerciseHistory(exerciseId: string) {
    const response = await fetch(`${API_URL}/exercises/${exerciseId}/exerciseHistory`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
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
    const response = await fetch(`${API_URL}/muscleGroups`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
    });
    const result = await response.json();
    return result;
};

/*
* END MUSCLE GROUPS
*/