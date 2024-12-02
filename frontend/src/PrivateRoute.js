import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ element }) => {
  const login = localStorage.getItem('login');
  const location = useLocation();

  if ((login !== 'doctor' && (location.pathname === '/doctor' || location.pathname.startsWith('/informacionPaciente/')
      || location.pathname ==='/metricas' || location.pathname === '/progreso' 
      || location.pathname === '/borrar' || location.pathname === '/cambiar')) ||
      (login !== 'paciente' && (location.pathname === '/paciente' || location.pathname === '/test'))) {
        return <Navigate to="/" />;
      }
  return element;
};

export default PrivateRoute;
