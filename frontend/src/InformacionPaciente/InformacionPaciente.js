import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { BarChart } from "../components/BarChart";
import Header from '../components/header/Header';
import './InformacionPaciente.css';
import TextoIA from "../components/textosIA/TextoIA.js"

const InformacionPaciente = () => {
    const idPaciente = localStorage.getItem('idPaciente');
    const [paciente, setPaciente] = useState([]);
    const [diagnostico, setDiagnostico] = useState(null);
    const navigate = useNavigate();

    const [chartDailyData, setChartDailyData] = useState({
        labels: [],
        datasets: [
            {
                title: {
                    text: "Primer Diagnóstico"},
                label: "Puntuación",
                data: [],
                backgroundColor: [
                    "rgba(75,192,192,1)",
                    "#ecf0f1",
                    "#50AF95",
                    "#f3ba2f",
                    "#2a71d0",
                    "#f32f2f"
                ],
                borderColor: "black",
                borderWidth: 2
            }
        ]
    });

    function setdatadoctor(diagnostico) {
        if (!diagnostico) return [];
        const schizoArray = [
            diagnostico.delusion,
            diagnostico.alucinaciones,
            diagnostico.hablaIncoherente,
            diagnostico.movimientoInusual,
            diagnostico.socializacion,
            diagnostico.higiene
        ];
        return schizoArray;
    }

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch(`${process.env.REACT_APP_API_URL}/medico/info/paciente/${idPaciente}`);
            const data = await response.json();
            setPaciente(data);
        };
        const fetchDiagnostico = async () => {
            const responseD = await fetch(`${process.env.REACT_APP_API_URL}/medico/info/diagnosticoInicial/${idPaciente}`);
            const dataD = await responseD.json();
            setDiagnostico(dataD);
        };
        fetchDiagnostico();
        fetchData();
    }, [idPaciente]);

    useEffect(() => {
        if (diagnostico) {
            const schizoArray = setdatadoctor(diagnostico);
            setChartDailyData({
                labels: [
                    "Delirios", "Alucinaciones", "Habla Incoherente", 
                    "Movimiento Inusual", "Socialización", "Sintomas negativos"
                ],
                datasets: [
                    {
                        label: "Puntuación",
                        data: schizoArray,
                        backgroundColor: [
                            "rgba(75,192,192,1)",
                            "#ecf0f1",
                            "#50AF95",
                            "#f3ba2f",
                            "#2a71d0",
                            "#f32f2f"
                        ],
                        borderColor: "black",
                        borderWidth: 2
                    }
                ]
            });
        }
    }, [diagnostico]);

    function VerProgreso() {
        navigate('/progreso');
    }

    function VerMetricas() {
        navigate('/metricas');
    }

    const inicio = {
        6: "El diagnóstico inicial de Victor nos indica que es alguien que presente ningun alto riesgo, pero si que tiene un riesgo en particular en cuanto a los síntomas negativos, se refiere, y requerira atención en ese ámbito, con tratamientos que ayuden al mantenimiento de una rutina y hábitos, junto con estimulación de las habilidades cognitivas.",
        7: "El diagnóstico inicial de Juaquon nos inidca un mayor riesgo que la média, teniendo en particular mas agravados los delirios, las alucinaciones y los sintomas negativos, requerira la medicación pertinente para prevenir el desarrollo de psicosis o paranoia, también requerira un seguimiento mas de cerca para asegurar un desarrollo favorable",
        1: "El diagnóstico inicial de Juana presenta un riesgo moderado, sin embargo no hay ningún ambito en el que se pueda decir que destaque por encima de la media, por lo que se tendrá que descartar hacer un tratamiento mas preciso, y tratar de aplicar uno mas general para las condiciones que presenta, aunque pueda ser menos efectivo en cada area.",
    };

    const [inicioTexto, setInicioTexto] = useState('');

    useEffect(() => {
        if (idPaciente === "6") {
            setInicioTexto(inicio[0]);
        } else if (idPaciente === "7") {
            setInicioTexto(inicio[1]);
        } else if (idPaciente === "1") {
            setInicioTexto(inicio[2]);
        } else {
            setInicioTexto("ID no encontrado");
        }
      }, [idPaciente]);

    return (
        <div>
            <Header />
            <div className="doctor-informacion-paciente">
                <div className="doctor-div-informacion">
                    <h2>Información del Paciente: {paciente.nombre}</h2>
                    <h3>Datos de Contacto</h3>
                    <p>Teléfono del Paciente: {paciente.telefonoPaciente}</p>
                    <p>Teléfono de Emergencia: {paciente.telefonoEmergencia}</p>

                    <h3>Diagnóstico y Métricas</h3>
                    <p>Puntaje: {paciente.squizoPoints}</p>
                    <p>Métrica más crítica: {paciente.worstMetric}</p>
                </div>
                <div className="doctor-div-botones">
                    <button className="doctor-botones" onClick={VerProgreso}>Ver Progreso</button>
                    <button className="doctor-botones" onClick={VerMetricas}>Ver Métricas Diarias</button>
                </div>
                <h3>Métricas Detalladas</h3>
                <div className="doctor-graficas">
                    <BarChart chartData={chartDailyData} />
                </div>
                <div class="wrapper">
                <div class="typing-demo3"> 
                <p>El diagnóstico inicial de Victor nos indica que es alguien que presente ningun alto riesgo. </p>
                </div>
                </div>
            </div>
            <div>
                <button className="informacionPaciente-generar-button" type ="button">Generar análisis con inteligengia artificial</button>
            </div>
        </div>
    );
};

export default InformacionPaciente;
