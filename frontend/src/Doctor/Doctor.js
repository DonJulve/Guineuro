import './Doctor.css';
import { useNavigate } from 'react-router-dom';
import Header from '../components/header/Header';
import ListaPacientes from '../components/ListaPacientes/ListaPacientes.js'

const Doctor = () => {
  const navigate = useNavigate();

  function handleBorrarPaciente() {
    navigate('/borrar');
  }

  function handleCambiarDeMedico() {
    navigate('/cambiar');
  }
    
  return (
    <div>
        <Header />
        <div>
            <h2 className="doctor-saludo">¡Hola, doctor!</h2>
        </div>
        <div className="doctor-div-botones-admin">
            <button className="doctor-botones-admin" onClick={handleBorrarPaciente}>Borrar paciente</button>
            <button className="doctor-botones-admin" onClick={handleCambiarDeMedico}>Cambiar de medico</button>
        </div>
        <div className="doctor-lista-pacientes">
            <h3>Lista de pacientes</h3>
            <ListaPacientes/>
        </div>
    </div>
  );
};

export default Doctor;
