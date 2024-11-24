import './Metricas.css';
import Header from '../components/header/Header';
import { BarChart } from "../components/BarChart";
import { useState, useEffect } from 'react';
import TextoIA from "../components/textosIA/TextoIA.js"

const Metricas = () => {
    const idPaciente = localStorage.getItem('idPaciente');
    const [diagnostico, setDiagnostico] = useState([]);

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
            const response = await fetch(`${process.env.REACT_APP_API_URL}/medico/metricas/${idPaciente}`);
            const data = await response.json();
            setDiagnostico(data);
        };
        fetchData();
    }, [idPaciente]);

    const generateChartsData = () => {
        return diagnostico.map((entry, index) => {
            const schizoArray = setdatadoctor(entry);
            return {
                labels: [
                    "Delirios", "Alucinaciones", "Habla Incoherente", 
                    "Movimiento Inusual", "Socialización", "Sintomas negativos"
                ],
                datasets: [
                    {
                        label: `Puntuación - Timestamp ${entry.timestamp}`,
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
            };
        });
    };

    const day = [
        "En este día se ha de destacar que Victor presenta unos sintomas negativos altamente irregulares, junto un habla incoherente al alza.",
        "En este día se ha de destacar que Victor presenta un empeoramiento generalizado de sus sintomas, destacando las alucinaciones.",
        "En este día se ha de destacar que Victor presenta una mejora generalizada, particularmente en el habla y movimientos nerviosos.",
        "En este día se ha de destacar que Victor presenta una mejora generalizada, particularmente en el habla y movimientos nerviosos."
    ];




    return(
        <div>
            <Header/>
            {diagnostico.length > 0 ? generateChartsData().map((chartData, index) => (
                <div key={index} style={{ marginBottom: '30px' }}>
                    <BarChart chartData={chartData} />
                    <button className="metricas-boton-generar" type= "button"> Generar análisis con inteligencia artificial</button>
                    <div class="wrapper">
      <div class="typing-demo"> 
      En este día se ha de destacar que Victor presenta unos sintomas negativos altamente irregulares, junto un habla incoherente al alza.
      </div>
      </div>
                </div>
            )) : <p>Cargando datos...</p>}
        </div>
    );
};

export default Metricas;
