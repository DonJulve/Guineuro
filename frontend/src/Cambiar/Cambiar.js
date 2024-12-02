import './Cambiar.css';
import Header from '../components/header/Header';
import { useState } from 'react';

const Cambiar = () => {
  const [nombreDeTuPaciente, setNombreDeTuPaciente] = useState('');
  const [nombreNuevoDoctor, setNombreNuevoDoctor] = useState('');
  const [loading, setLoading] = useState(false);

  // Función para manejar el cambio de nombre del paciente
  const handleNombrePaciente = (e) => {
    setNombreDeTuPaciente(e.target.value);
  };

  // Función para manejar el cambio de nombre del nuevo doctor
  const handleNombreNuevoDoctor = (e) => {
    setNombreNuevoDoctor(e.target.value);
  };

  // Función para realizar las solicitudes y cambiar el médico
  const guardar = async () => {

      // Obtener el ID del paciente
      const responsePaciente = await fetch(`http://192.168.1.47:8080/paciente/login/${nombreDeTuPaciente}`);
      
      const dataPaciente = await responsePaciente.json();

      // Obtener el ID del nuevo doctor
      const responseDoctor = await fetch(`http://192.168.1.47:8080/medico/login/${nombreNuevoDoctor}`);
      
      const dataDoctor = await responseDoctor.json();
      //-----------------------------------

      const requestData = {
        ID_paciente: dataPaciente,
        ID_medico: dataDoctor,
      };
      // Enviar la solicitud para cambiar el médico
      const responseCambio = await fetch('http://192.168.1.47:8080/medico/cambiarMedico', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestData),
      });
      console.log(requestData);
    
  };

  return (
    <>
      <Header />
      <div className="cambiar-div-inputs">
        <input
          className="cambiar-paciente"
          type="text"
          placeholder="Nombre del paciente"
          autoComplete="off"
          value={nombreDeTuPaciente}
          onChange={handleNombrePaciente}
        />

        <input
          className="cambiar-nuevo-doctor"
          type="text"
          placeholder="Nombre del nuevo doctor"
          autoComplete="off"
          value={nombreNuevoDoctor}
          onChange={handleNombreNuevoDoctor}
        />

        <button
          className="guardar"
          onClick={guardar}
          disabled={loading}
        >
          {loading ? 'Cargando...' : 'Buscar y Cambiar'}
        </button>
      </div>
    </>
  );
};

export default Cambiar;
