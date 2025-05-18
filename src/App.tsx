import './App.css';
import { Helmet } from 'react-helmet';
import { AuthContext } from './auth/AuthContext';
import { useContext } from 'react';
import { AuthContextType } from './types';
import Layout from "./layouts/Layout";
import AppRoutes from "./routes/AppRoutes";
import CustomThemeProvider from './theme/ThemeProvider';

export default function App() {
  // Gets loading status from context
  const { loading } = useContext<AuthContextType>(AuthContext);

  if (loading) {
    return null;
  }

  return (
    <CustomThemeProvider>
      <Helmet>
        <title>Rise & Grind</title>
      </Helmet>
      <Layout>
        <AppRoutes />
      </Layout>
    </CustomThemeProvider>
  )
}