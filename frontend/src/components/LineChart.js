import React from "react";
import { Chart } from "react-chartjs-2";
import { Line } from "react-chartjs-2";
import { Data, Id } from "../utils/Data copy";
import './LineChart.css'

export const LineChart = ({ chartData }) => {
  return (
    <div className="chart-container">
      <h2 style={{ textAlign: "center" }}>Metricas Semanales</h2>
      <Line
        data={chartData}
        options={{
          scales: {
            y: {
              max: 100
            }
          },
          plugins: {
  },
            title: {
              display: true,
              text: "Progreso del paciente"
            },
            legend: {
              display: false
            },
                tooltip: {
                    callbacks: {
                        label: ((tooltipItem, fullData) => {
                            const newLineArray = []
                            newLineArray.push("Dia " + (tooltipItem.dataIndex + 1))
                            if ( Id === tooltipItem.dataIndex) {
                                for(let i in Data) {
                                    newLineArray.push(Data[i].metric + ": " + Data[i].schizoscore)
                                }
                            }
                            return newLineArray
                        })
                    }
                },
          elements: {
                point: {
                    radius: 4,
                    hitRadius: 20,
                    hoverRadius: 20,
                    hoverBorderWidth: 4,
                }
            }
        }}
      />
    </div>
  );
}