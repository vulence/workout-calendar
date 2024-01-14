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
}

export async function fetchWorkouts() {
    const response = await fetch(`${API_URL}/workouts`, {
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

export async function submitWorkoutExerciseDone(workoutId : string, exerciseDoneDto : any) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises/new`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        credentials: 'include',
        body: JSON.stringify(exerciseDoneDto),
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

export async function deleteWorkoutExerciseDone(workoutId : string, exerciseDoneId : string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises`, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(exerciseDoneId),
    });

    return response.status;
}

export async function updateWorkoutExerciseDone(workoutId : string, exerciseDone : any) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/exercises/update`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(exerciseDone),
    });

    return response.status;
};

export async function updateExerciseDoneCompleted(workoutId : string, exerciseDoneId: string, completed: boolean) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/setCompleted`, {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify({ exerciseDoneId, completed })
    });

    return response.status;
};

export async function setRating(workoutId : string, rating : number) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/setRating`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(rating)
    });

    return response.status;
};

export async function setDuration(workoutId : string, minutes : number) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/setDuration`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: JSON.stringify(minutes)
    });

    return response.status;
};

export async function setNotes(workoutId : string, notes : string) {
    const response = await fetch(`${API_URL}/workouts/${workoutId}/setNotes`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${Userfront.tokens.accessToken}`
        },
        body: notes
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