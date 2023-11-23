import { Routes, Route, Navigate } from "react-router-dom";
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
import Register from './register/Register';
import theme from './theme';

import { AuthContext } from './auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType } from './types';

export default function App() {
  // Gets user authentication status from context
  const { authenticated } = useContext<AuthContextType>(AuthContext);

  return (
    <ThemeProvider theme={theme}>
      <div className="App">
        <Helmet>
          <title>Workout Calendar</title>
        </Helmet>

        <Appbar />

        <Routes>
          <Route path="/login" element={!authenticated ? <Login /> : <Navigate to="/home" />} />
          <Route path="/register" element={!authenticated ? <Register /> : <Navigate to="/home" />} />
          <Route path="*" element={!authenticated ? <Navigate to="/login" /> : null} />

          {authenticated &&
            <>
              <Route path="/" element={<Navigate to="/home" />} />
              <Route path="/home" element={<Home />} />
              <Route path="/exercises" element={<AllExercises />} />
              <Route path="/exercises/:id/history" element={<ExerciseHistory />} />
              <Route path="/workouts" element={<AllWorkouts />} />
              <Route path="/workouts/:id" element={<AWorkout />} />
              <Route path="/musclegroups" element={<AllMuscleGroups />} />
            </>
          }
        </Routes>
      </div>
    </ThemeProvider>
  )
}