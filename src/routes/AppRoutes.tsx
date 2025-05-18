import { useContext } from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { AuthContextType } from "../types";
import { AuthContext } from "../auth/AuthContext";
import Login from '../auth/Login';
import Register from '../auth/Register';
import ExerciseHistory from '../exercise/ExerciseHistory';
import Dashboard from '../dashboard/Dashboard';
import AllExercises from '../exercise/AllExercises';
import AllWorkouts from '../workouts/allworkouts/AllWorkouts';
import Workout from '../workouts/workout/Workout';
import Logout from "../auth/Logout";
import Account from "../account/Account";
import Landing from "../landing/Landing";

export default function AppRoutes() {
    const { authenticated } = useContext<AuthContextType>(AuthContext);

    return (
        <Routes>
            <Route path="/" element={!authenticated ? <Landing /> : <Navigate to="/dashboard" />} />
            <Route path="*" element={!authenticated ? <Navigate to="/" /> : null} />
            <Route path="/login" element={!authenticated ? <Login /> : <Navigate to="/dashboard" />} />
            <Route path="/register" element={!authenticated ? <Register /> : <Navigate to="/dashboard" />} />

            {authenticated &&
                <>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/exercises" element={<AllExercises />} />
                    <Route path="/exercises/:id" element={<ExerciseHistory />} />
                    <Route path="/workouts/?" element={<AllWorkouts />} />
                    <Route path="/workouts/:id" element={<Workout />} />
                    <Route path="/account" element={<Account />} />
                    <Route path="/logout" element={<Logout />} />
                </>
            }
        </Routes>
    );
}