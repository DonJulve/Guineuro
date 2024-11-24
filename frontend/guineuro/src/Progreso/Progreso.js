import './Progreso.css';
import React, { useEffect, useState } from 'react';
import Header from '../components/header/Header';
import { LineChart } from "../components/LineChart";
import TextoIA from "../components/textosIA/TextoIA.js"

const Progreso = () => {
  const [diagnostico, setDiagnostico] = useState(null);
  const [chartLongData, setChartLongData] = useState({
    labels: [],
    datasets: [
      {
        label: "Puntuación",
        data: [],
        backgroundColor: "grey",
        borderColor: "black",
        borderWidth: 2
      }
    ]
  });

  const idPaciente = localStorage.getItem('idPaciente');

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch(`${process.env.REACT_APP_API_URL}/medico/metricas/${idPaciente}`);
      const data = await response.json();
      setDiagnostico(data);  // Guarda los datos en el estado
    };
    fetchData();
  }, [idPaciente]);


  function setdatadoctor(diagnosticos) {
    if (!diagnosticos || diagnosticos.length === 0) return [];
    
    const resultados = [];

    diagnosticos.forEach(diagnostico => {
      const schizoArray = [
        diagnostico.delusion,
        diagnostico.alucinaciones,
        diagnostico.hablaIncoherente,
        diagnostico.movimientoInusual,
        diagnostico.socializacion,
        diagnostico.higiene
      ];

      const sumaTotal = (schizoArray.reduce((sum, value) => sum + value, 0)) / 6;
      resultados.push(sumaTotal);
    });
    return resultados;
  }

    function setfullArray(diagnosticos) {
        const fullArray = [];
        if (!diagnosticos || diagnosticos.length === 0) return [];
        diagnosticos.forEach(diagnostico => {
            fullArray.push([
                diagnostico.timestamp,
                diagnostico.delusion,
                diagnostico.alucinaciones,
                diagnostico.hablaIncoherente,
                diagnostico.movimientoInusual,
                diagnostico.socializacion,
                diagnostico.higiene
            ]);
        });
        return fullArray;
        
    };



  useEffect(() => {
    if (diagnostico) {
      const schizoArray = setdatadoctor(diagnostico);
      const schizoLabels = []
      for (let i = 0; i < schizoArray.length; i++) {
        schizoLabels.push("Día " + (i+1));
      }
      setChartLongData({
        labels: schizoLabels,
        datasets: [
          {
            label: "Puntuación",
            data: schizoArray,
            backgroundColor: "grey",
            borderColor: "black",
            borderWidth: 2
            
          }
        ]
      });
    }
  }, [diagnostico]);

  const prog = [ "Victor presenta un progreso positivo a lo largo del tiempo, aun con un empeoramiento inicial, la aplicación del tratamiento ha logrado revertir la tendencia inicial. A lo largo de los primeros 5 días de tratamiento se puede ver una tendencia que se estabiliza hacia una normalidad, limitando los síntomas del transtorno. A pesar de una puntuación general no alarmante, su alto deterioro de los sintomas negativos y depreciación de sus sintomas cognitivos, nos lleva a tratar un tratamiento mas práctico, con ejercicio que fortalezcan las habilidades cognitivas y tareas que motiven al cumplimiento de las necesidades básicas.","Juaquon ha empezado hace poco con los diagnósticos, pero ya presenta una tendencia hacia deterioro general, como se ha podido presenciar en otros pacientes si no se lleva a cabo un tratamiento especializado podria continuar esta tendencia, presenta un alto riesgo en el ambito de alucinaciones, por lo que se habría de valorar recetar la medicina correspondiente. El ámbito de socialización y sintomas negativos no presentan  un estado crítico, pero si descuidados podrían tender a situaciones similares a otros pacientes que han sufrido mayor deterioro. Hemos de motivar el continuo uso de la app y seguimiento de su estado","Juana presenta un deterioro grave y de forma continua, incluso con algunas instancias en las que presentaba cierta mejora, esta no ha logrado presentarse de una forma persistente. Se debería tratar de intensificar los tratamientos disponibles, una combinación de las medicaciones pertinentes y actividades que puedan motivar el ejercicio de las habilidades cognitivas necesarias, tratar de tener sesiones mas frecuentes y ejercer un seguimiento mas serio de la paciente. Su progreso hasta el momento ha demostrado no ser particularmente peor en ninguna de las métricas, por lo que es mas dificil establecer un plan especifico.",
  ];

  const [ProgresoTexto, setProgresoTexto] = useState('');

  useEffect(() => {
    if (idPaciente === "6") {
      setProgresoTexto(prog[0]);
    } else if (idPaciente === "7") {
      setProgresoTexto(prog[1]);
    } else if (idPaciente === "1") {
      setProgresoTexto(prog[2]);
    } else {
      setProgresoTexto("ID no encontrado");
    }
  }, [idPaciente]);

  return (
    <div>
      <Header />
      <LineChart className="proceso-grafico" width={500} height={300} chartData={chartLongData} />
      <button className="progreso-generar-button" type ="button"> Generar análisis con inteligengia artificial</button>
      <div class="wrapper">
        <div class="typing-demo"> 
          <p>{ProgresoTexto}</p>
        </div>
      </div>
    </div>
  );
};

export default Progreso;
