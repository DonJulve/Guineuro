import React, { useState, useEffect } from 'react';
import Header from '../components/header/Header';
import './Paciente.css';
import { useNavigate } from 'react-router-dom';

const Paciente = () => {
  const navigate = useNavigate();
  const idPaciente = localStorage.getItem('idPaciente');
  
  // Estado para los datos del paciente y el mensaje del chatbot
  const [infoPaciente, setInfoPaciente] = useState([]);
  const [mensaje, setMensaje] = useState("");  // Para almacenar el mensaje del usuario
  const [respuestaBot, setRespuestaBot] = useState("");  // Para almacenar la respuesta del chatbot

  // Función para manejar el test diario
  function handleTestDiario() {
    navigate('/test');
  }

  // Función para manejar el botón del pánico
  function handleBotonDelPanico() {
    fetch(`${process.env.REACT_APP_API_URL}/paciente/botonPanico/${idPaciente}`);
    alert('Se ha enviado una alerta a emergencias');
  }

  // Función para obtener los datos del paciente
  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/paciente/info/paciente/${idPaciente}`);
      const data = await response.json();
      setInfoPaciente(data);
    };
    fetchData();
  }, [idPaciente]);

  // Función para manejar el envío del mensaje al chatbot
  const handleEnviarMensaje = async (event) => {
    event.preventDefault();  // Evitar el refresco de la página
    if (mensaje.trim()) {
      try {
        // Hacer la petición al chatbot
        const response = await fetch('http://localhost:4444/guineuro/respond', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({ message: mensaje }),
        });
        const data = await response.json();
        setMensaje("");
        setRespuestaBot(data.response);  // Almacenar la respuesta del chatbot
      } catch (error) {
        console.error("Error al contactar con el chatbot:", error);
      }
    }
  };

  return (
    <>
      <Header />
      <div className="paciente-todo">
        <div className="paciente-datos">
          <img 
            heigth="50px" 
            src={`${process.env.REACT_APP_API_URL}/paciente/fotos/pacientes/${infoPaciente.id}`}
            alt={`Foto de ${infoPaciente.nombre}`} 
            className="paciente-photo" 
          />
          <p className="paciente-nombre">¡Hola {infoPaciente.nombre}!</p>
          <p className="paciente-telefono-emergencia">Tu teléfono de emergencia es {infoPaciente.telefonoEmergencia}</p>
          <p className="paciente-mi-telefono">Tu teléfono es {infoPaciente.telefonoPaciente}</p>
        </div>
        
        <div className="paciente-home">
          <button className="paciente-home-botones" onClick={handleTestDiario}>
            Realizar test diario
          </button>
          <button className="paciente-home-boton-del-panico" onClick={handleBotonDelPanico}>
            Botón del pánico
          </button>
        </div>
        
        {/* Chatbot */}
        <div className="paciente-chatbot">
          <h3>Guineuro</h3>
          <form onSubmit={handleEnviarMensaje}>
            <input
              type="text"
              value={mensaje}
              onChange={(e) => setMensaje(e.target.value)}
              placeholder="Escribe tu mensaje"
              className="paciente-chatbot-input"
            />
            <button type="submit" className="paciente-chatbot-boton">
              Enviar
            </button>
          </form>
          
          {respuestaBot && (
            <div className="paciente-chatbot-respuesta">
              <p><strong>Respuesta del chatbot:</strong> {respuestaBot}</p>
            </div>
          )}
        </div>
      </div>
    </>
  );
};

export default Paciente;
