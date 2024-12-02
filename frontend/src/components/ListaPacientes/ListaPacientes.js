import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useState } from 'react';
import './ListaPacientes.css'




const ListaPacientes = () => {
    const [pacientes, setPacientes] = useState([]);
    const navigate = useNavigate();



    const idDoctor = localStorage.getItem('idDoctor');

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/medico/pacientes/${idDoctor}`);
            const data = await response.json();
            setPacientes(data);
        };
    
        fetchData();
    }, [idDoctor]);


    


    function irAPaciente(id) {
        localStorage.setItem("idPaciente", id);
        navigate(`/informacionPaciente/${id}`);
    }

    return (
        <div>
            <ul className="doctor-lista-pacientes">
                {pacientes.length > 0 ? (
                    pacientes.map((paciente) => (
                        <li
                            key={paciente.id}
                            className="doctor-lista-pacientes-persona"
                            onClick={() => irAPaciente(paciente.id)}
                            
                        >
                            <img heigth="50px" src={`${process.env.REACT_APP_API_URL}/medico/fotos/${paciente.id}`}
                                alt={`Foto de ${paciente.nombre}`} 
                                className="patient-photo" 
                            />
                            <div className="doctor-lista-pacientes-persona-nombre">{paciente.nombre}</div>
                            
                        </li>
                    ))
                ) : (
                    <p className="doctor-lista-sin-pacientes">No hay pacientes disponibles.</p>
                )}
            </ul>
            
        </div>
    );
};

export default ListaPacientes;