import { Routes, Route, Navigate } from "react-router-dom";
import './App.css';
import Appbar from './Appbar';
import Footer from './Footer';
import { Helmet } from 'react-helmet';
import {
  experimental_extendTheme as materialExtendTheme,
  Experimental_CssVarsProvider as MaterialCssVarsProvider,
  THEME_ID as MATERIAL_THEME_ID,
} from '@mui/material/styles';
import { CssVarsProvider as JoyCssVarsProvider } from '@mui/joy/styles';
import theme from './theme';
import joyTheme from './joyTheme';

import Login from './auth/Login';
import Register from './auth/Register';
import ExerciseHistory from './exercise/ExerciseHistory';
import Dashboard from './dashboard/Dashboard';
import AllExercises from './exercise/AllExercises';
import AllWorkouts from './workouts/allworkouts/AllWorkouts';
import Workout from './workouts/workout/Workout';
import Logout from "./auth/Logout";
import Account from "./account/Account";
import Landing from "./landing/Landing";;
import { AuthContext } from './auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType } from './types';

const materialTheme = materialExtendTheme(theme);

export default function App() {
  // Gets user authentication status and loading status from context
  const { authenticated, loading } = useContext<AuthContextType>(AuthContext);

  if (loading) {
    return null;
  }

  return (
    <MaterialCssVarsProvider theme={{ [MATERIAL_THEME_ID]: materialTheme }}>
      <JoyCssVarsProvider theme={joyTheme}>
        <div className="App">
          <Helmet>
            <title>Rise & Grind</title>
          </Helmet>

          <header>
            <Appbar />
          </header>

          <main>
            <Routes>
              <Route path="/" element={!authenticated ? <Landing /> : <Navigate to="/dashboard" />} />
              <Route path="*" element={!authenticated ? <Navigate to="/" /> : null} />
              <Route path="/login" element={!authenticated ? <Login /> : <Navigate to="/dashboard" />} />
              <Route path="/register" element={!authenticated ? <Register /> : <Navigate to="/dashboard" />} />

              {authenticated &&
                <>
                  <Route path="/dashboard" element={<Dashboard />} />
                  <Route path="/exercises" element={<AllExercises />} />
                  <Route path="/exercises/:id/history" element={<ExerciseHistory />} />
                  <Route path="/workouts/?" element={<AllWorkouts />} />
                  <Route path="/workouts/:id" element={<Workout />} />
                  <Route path="/account" element={<Account />} />
                  <Route path="/logout" element={<Logout />} />
                </>
              }
            </Routes>
          </main>

          <footer>
            <Footer />
          </footer>
        </div>
      </JoyCssVarsProvider>
    </MaterialCssVarsProvider>
  )
}