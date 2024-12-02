// components/BarChart.js
import { Bar } from "react-chartjs-2";
export const BarChart = ({ chartData }) => {
  return (
    <div className="chart-container">
      <h2 style={{ textAlign: "center" }}>Bar Chart</h2>
      <Bar
        data={chartData}
        options={{
          scales: {
            y: {
              max: 100
            }
          },
          plugins: {
            title: {
              display: true,
              text: "Primer Diagnóstico",
            },
            legend: {
              onClick: (evt, legendItem, legend) => {
                const index = legend.chart.data.labels.indexOf(legendItem.text)
                legend.chart.toggleDataVisibility(index)
                legend.chart.update()
              },
              display: true,
              position: "top",
              title: {
                text: "Metricas",
                display: true
              },
              labels: {
                generateLabels: (chart) => {
                  return chart.data.labels.map(
                    (label, index) => ({
                      text: label,
                      strokeStyle: chart.data.datasets[0].borderColor[index],
                      fillStyle: chart.data.datasets[0].backgroundColor[index],
                      hidden: false
                    })
                  )
                }
              }
            }
          }
        }}
      />
    </div>
  );
};