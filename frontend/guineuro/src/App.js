import React, { useState } from 'react';
import logo from './assets/esquizorro.png';
import Chart from "chart.js/auto";

import { Route, Routes, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { CategoryScale } from "chart.js";

import './App.css';
import Doctor from './Doctor/Doctor';
import Paciente from './Paciente/Paciente';
import PrivateRoute from './PrivateRoute';
import InformacionPaciente from './InformacionPaciente/InformacionPaciente';
import Test from './Test/Test';
import Progreso from './Progreso/Progreso';
import Metricas from './Metricas/Metricas';
import Borrar from './Borrar/Borrar';
import Cambiar from './Cambiar/Cambiar';


function App() {
 
  const navigate = useNavigate();
  const [login, setLogin] = useState('nada'); 
 

  Chart.register(CategoryScale);

  function showLoginDoctor() {
    setLogin('doctor'); 
  }

  function showLoginPaciente() {
    setLogin('paciente'); 
  }

  const [usernameDoctor, setUsernameDoctor] = useState(''); 
  const [passwordDoctor, setPasswordDoctor] = useState('');
  const [loginErrorDoctor, setLoginErrorDoctor] = useState('');

  // Estados para paciente
  const [usernamePaciente, setUsernamePaciente] = useState('');
  const [passwordPaciente, setPasswordPaciente] = useState('');
  const [loginErrorPaciente, setLoginErrorPaciente] = useState('');

  function handleDoctorUsernameChange(event) {
    setUsernameDoctor(event.target.value);
    setLoginErrorDoctor('');
  }

  function handleDoctorPasswordChange(event) {
    setPasswordDoctor(event.target.value);
    setLoginErrorDoctor('');
  }

  function handlePacienteUsernameChange(event) {
    setUsernamePaciente(event.target.value);
    setLoginErrorDoctor('');
  }

  function handlePacientePasswordChange(event) {
    setPasswordPaciente(event.target.value);
    setLoginErrorDoctor('');
  }


  async function LoginDoctor() {
    if (!usernameDoctor || !passwordDoctor) {
      setLoginErrorDoctor('Por favor, introduce tu usuario y contraseña');
      return;
    }    
    const doc = await IdDoctor();
    if (doc) {
      localStorage.setItem('login', 'doctor');
      setUsernameDoctor('');
      setPasswordDoctor('');
      setLogin('nada');
      navigate('/doctor');
    }
  }

  async function LoginPaciente() {
    if (!usernamePaciente || !passwordPaciente) {
      setLoginErrorPaciente('Por favor, introduce tu usuario y contraseña');
      return;
    }
    const pac = await IdPaciente();
    if (pac) {
      localStorage.setItem('login', 'paciente');
      setUsernamePaciente('');
      setPasswordPaciente('');
      setLogin('nada');
      navigate('/paciente');
    }
  }

  const IdDoctor = async () => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/medico/login/${usernameDoctor}`);
    const data = await response.json();
    if (data === -1) {
      setLoginErrorDoctor('El usuario no existe');
      return false;
    }
    localStorage.setItem('idDoctor', data);
    return true;
  };

  const IdPaciente = async () => {
    const response = await fetch(`${process.env.REACT_APP_API_URL}/paciente/login/${usernamePaciente}`);
    const data = await response.json();
    if (data === -1) {
      setLoginErrorPaciente('El usuario no existe');
      return false;
    }
    localStorage.setItem('idPaciente', data);
    return true;
  };

  return (
    <div>
      <Routes>
        <Route path="/doctor" element={<PrivateRoute element={<Doctor />} />} />
        <Route path="/paciente" element={<PrivateRoute element={<Paciente />} />} />
        <Route path="/informacionPaciente/:id" element={<PrivateRoute element={<InformacionPaciente />} />} />
        <Route path="/test" element={<PrivateRoute element={<Test />} />} />
        <Route path="/progreso" element={<PrivateRoute element={<Progreso />} />} />
        <Route path="/metricas" element={<PrivateRoute element={<Metricas />} />} />
        <Route path="/borrar" element={<PrivateRoute element={<Borrar />} />} />
        <Route path="/cambiar" element={<PrivateRoute element={<Cambiar />} />} />
        <Route path="/" element={
          <div>
            <div className="App">
              <header className="App-header">
                <img src={logo} className="App-logo" alt="logo" />
                <p>
                  <h1 className="App-name">Guineuro</h1>
                </p>
                
              </header>
            </div>
            <div className="App-choose-user">
              {login === 'nada' && (
                <>
                <button className="App-button-choosing" onClick={showLoginDoctor}>Soy doctor</button>
                <button className="App-button-choosing" onClick={showLoginPaciente}>Soy paciente</button>
                </>
              )}
              {login === 'doctor' && (
                <div className="App-login-doctor">
                  <FontAwesomeIcon 
                    icon={faArrowLeft} 
                    className="App-button-choosing-go-back" 
                    onClick={() => {setLogin('nada'); setUsernameDoctor(''); setPasswordDoctor('');}}
                  />
                  <h2 className="App-title-login">Iniciar sesión como doctor</h2>
                  <input
                    className='App-input-username-doctor'
                    type="text"
                    id="dni"
                    placeholder='Nombre'
                    autoComplete='off'
                    value={usernameDoctor}
                    onChange={handleDoctorUsernameChange}
                  />
                  <input
                    className='App-input-password-doctor'
                    type="password"
                    id="password"
                    placeholder='Contraseña'
                    value={passwordDoctor}
                    onChange={handleDoctorPasswordChange}
                  />
                  {loginErrorDoctor && <p className="error-message">{loginErrorDoctor}</p>}

                  <button className="App-button-enter" onClick={LoginDoctor}>Entrar</button>
                </div> 
              )}
              {login === 'paciente' && (
                <div className="App-login-paciente">
                  <FontAwesomeIcon 
                    icon={faArrowLeft} 
                    className="App-button-choosing-go-back" 
                    onClick={() => {setLogin('nada'); setUsernamePaciente(''); setPasswordPaciente('');}}
                  />
                  <h2 className="App-title-login">Iniciar sesión como paciente</h2>
                  <input
                    className='App-input-username-paciente'
                    type="text"
                    id="DNI"
                    placeholder='Nombre'
                    autoComplete='off'
                    value={usernamePaciente}
                    onChange={handlePacienteUsernameChange}
                  />
                  <input
                    className='App-input-password-paciente'
                    type="password"
                    id="password"
                    placeholder='Contraseña'
                    value={passwordPaciente}
                    onChange={handlePacientePasswordChange}
                  />
                  {loginErrorPaciente && <p className="error-message">{loginErrorPaciente}</p>}
                  <button className="App-button-enter" onClick={LoginPaciente}>Entrar</button>
                </div>
              )}
            </div>
          </div>
        }/>
    </Routes>
    </div>
  );
}

export default App;
