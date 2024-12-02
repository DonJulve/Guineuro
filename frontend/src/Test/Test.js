import React, { useState, useEffect } from 'react';
import './Test.css';
import Header from '../components/header/Header';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons';
import { useNavigate } from 'react-router-dom';

const Test = () => {
    const navigate = useNavigate();

    const idPaciente = localStorage.getItem('idPaciente');
    const [preguntas, setPreguntas] = useState([]);
    const [opcionSeleccionada, setOpcionSeleccionada] = useState({});

    const opciones = ["Nada de acuerdo", "Poco de acuerdo", "Neutral", "Bastante de acuerdo", "Completamente de acuerdo"];

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`${process.env.REACT_APP_API_URL}/paciente/test/${idPaciente}`);
                const data = await response.json();
                localStorage.setItem('idTest', data.idTest);
                setPreguntas(data.preguntas || []); // Extrae el arreglo 'preguntas'
            } catch (error) {
                console.error("Error al obtener las preguntas:", error);
                setPreguntas([]); // En caso de error, asigna un arreglo vacío
            }
        };

        fetchData();
    }, [idPaciente]);

    const handleOptionChange = (pregunta, respuesta) => {
        const valorNumerico = opciones.indexOf(respuesta) + 1; // Convertir a número (1 al 5)
        setOpcionSeleccionada((prev) => ({
            ...prev,
            [pregunta]: valorNumerico, // Guardar el valor numérico
        }));
    };

    const handleSubmit = async () => {
        // Verifica si todas las preguntas han sido respondidas
        if (Object.keys(opcionSeleccionada).length === preguntas.length) {
            const idPaciente = localStorage.getItem('idPaciente');
            const idTest = localStorage.getItem('idTest');
            const requestData = {
                idPaciente,
                idTest,
                respuestas: opcionSeleccionada,
            };
            await fetch(`${process.env.REACT_APP_API_URL}/paciente/respuesta`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });
            alert('Gracias por responder el test. :)');
            localStorage.removeItem('idTest');
            navigate('/paciente');
        } else {
            alert('Por favor, responde a todas las preguntas antes de enviar.');
        }
    };

    function handleGoBack() {
        navigate('/paciente');
    }

    return (
        <>
            <Header />
            <div className="test-container">
                <FontAwesomeIcon icon={faArrowLeft} className="test-go-back" onClick={handleGoBack} />
                <h1 className="test-titulo">Test</h1>
                <form className="test-form">
                    {preguntas.map(({ id, texto }) => (
                        <div key={id} className="test-pregunta">
                            <h2 className="test-pregunta-pregunta">{texto}</h2>
                            {opciones.map((respuesta, index) => (
                                <label key={index} className="test-respuesta">
                                    <input
                                        type="radio"
                                        name={`pregunta-${id}`}
                                        value={respuesta}
                                        checked={opcionSeleccionada[id] === index + 1}
                                        onChange={() => handleOptionChange(id, respuesta)}
                                    />
                                    {respuesta}
                                </label>
                            ))}
                        </div>
                    ))}
                </form>
                <button
                    className="test-submit-button"
                    onClick={handleSubmit}
                    disabled={Object.keys(opcionSeleccionada).length !== preguntas.length} // Deshabilita si no se responden todas
                >
                    Enviar
                </button>
            </div>
        </>
    );
};

export default Test;
