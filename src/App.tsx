import React from 'react';
import {Routes, Route, Navigate} from "react-router-dom";
import './App.css';
import Appbar from './Appbar';
import AllExercises from './exercise/AllExercises';
import AllWorkouts from './workout/AllWorkouts';
import AWorkout from './workout/Workout';
import ExerciseHistory from './exercise/ExerciseHistory';
import AllMuscleGroups from './musclegroups/AllMuscleGroups';
import Home from './home/Home';
import { Helmet } from 'react-helmet';
import { ThemeProvider } from '@mui/material/styles';
import Login from './login/Login';
import theme from './theme';

export default function App() {
  return (
    <ThemeProvider theme={theme}>
        <div className = "App">
            <Helmet>
                <title>Workout Calendar</title>
            </Helmet>

            <Appbar />

            <Routes>
                <Route path= "/login" element={ <Login /> } />
                <Route path = "/" element={<Navigate to="/home" />} />
                <Route path = "/home" element = { <Home/> } />
                <Route path = "/exercises" element = { <AllExercises/> } />
                <Route path = "/exercises/:id/history" element = { <ExerciseHistory/> } />
                <Route path = "/workouts" element = { <AllWorkouts/> } />
                <Route path = "/workouts/:id" element = { <AWorkout/> } />
                <Route path = "/musclegroups" element = { <AllMuscleGroups/> } />
            </Routes>
        </div>
    </ThemeProvider>
  )
}