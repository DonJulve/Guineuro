package guineuro.hackaton.model;

import java.util.HashMap;
import java.util.Map;

public class Metricas {
    private Integer timestamp;
    private Float delusion;
    private Float alucinaciones;
    private Float hablaIncoherente;
    private Float movimientoInusual;
    private Float socializacion;
    private Float higiene;

    public Metricas(Metricas other) {
        this.timestamp = other.timestamp;
        this.delusion = other.delusion;
        this.alucinaciones = other.alucinaciones;
        this.hablaIncoherente = other.hablaIncoherente;
        this.movimientoInusual = other.movimientoInusual;
        this.socializacion = other.socializacion;
        this.higiene = other.higiene;
    }
    
    public Metricas(Float delusion, Float alucinaciones, Float hablaIncoherente, 
                    Float movimientoInusual, Float socializacion, Float higiene) {
        this.delusion = delusion;
        this.alucinaciones = alucinaciones;
        this.hablaIncoherente = hablaIncoherente;
        this.movimientoInusual = movimientoInusual;
        this.socializacion = socializacion;
        this.higiene = higiene;
    }

    public Metricas(Integer _timestamp,Float delusion, Float alucinaciones, Float hablaIncoherente, 
                    Float movimientoInusual, Float socializacion, Float higiene) {
        this.timestamp = _timestamp;
        this.delusion = delusion;
        this.alucinaciones = alucinaciones;
        this.hablaIncoherente = hablaIncoherente;
        this.movimientoInusual = movimientoInusual;
        this.socializacion = socializacion;
        this.higiene = higiene;
    }

    public Metricas() {
        this.delusion = 0.0f;
        this.alucinaciones = 0.0f;
        this.hablaIncoherente = 0.0f;
        this.movimientoInusual = 0.0f;
        this.socializacion = 0.0f;
        this.higiene = 0.0f;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Float getDelusion() {
        return delusion;
    }

    public void setDelusion(Float delusion) {
        this.delusion = delusion;
    }

    public Float getAlucinaciones() {
        return alucinaciones;
    }

    public void setAlucinaciones(Float alucinaciones) {
        this.alucinaciones = alucinaciones;
    }

    public Float getHablaIncoherente() {
        return hablaIncoherente;
    }

    public void setHablaIncoherente(Float habla_incoherente) {
        this.hablaIncoherente = habla_incoherente;
    }

    public Float getMovimientoInusual() {
        return movimientoInusual;
    }

    public void setMovimientoInusual(Float movimiento_inusual) {
        this.movimientoInusual = movimiento_inusual;
    }

    public Float getSocializacion() {
        return socializacion;
    }

    public void setSocializacion(Float socializacion) {
        this.socializacion = socializacion;
    }

    public Float getHigiene() {
        return higiene;
    }

    public void setHigiene(Float higiene) {
        this.higiene = higiene;
    }

   public Float getSquizoPoints(float ponderDelusion, float ponderAlucinaciones, float ponderHablaIncoherente,
                           float ponderMovimientosInusuales, float ponderSocializacion, float ponderHigiene) {

        Float sumaPonderaciones = ponderDelusion + ponderAlucinaciones + ponderHablaIncoherente
                                + ponderMovimientosInusuales + ponderSocializacion + ponderHigiene;

        Float resul = (this.getDelusion() * ponderDelusion +
                    this.getAlucinaciones() * ponderAlucinaciones +
                    this.getHablaIncoherente() * ponderHablaIncoherente +
                    this.getMovimientoInusual() * ponderMovimientosInusuales +
                    this.getSocializacion() * ponderSocializacion +
                    this.getHigiene() * ponderHigiene) / sumaPonderaciones;

        return resul;
    }

    public Float getSquizoPoints() {
        return getSquizoPoints(1, 1, 1, 1, 1, 1);
    }

    public String getWorstMetric() {
        // Crear un mapa para almacenar las métricas y sus valores
        Map<String, Float> metricValues = new HashMap<>();
        metricValues.put("delusion", this.getDelusion());
        metricValues.put("alucinaciones", this.getAlucinaciones());
        metricValues.put("hablaIncoherente", this.getHablaIncoherente());
        metricValues.put("movimientoInusual", this.getMovimientoInusual());
        metricValues.put("socializacion", this.getSocializacion());
        metricValues.put("higiene", this.getHigiene());

        // Determinar la métrica con el valor más alto
        String worstMetric = null;
        Float highestValue = Float.MIN_VALUE;

        for (Map.Entry<String, Float> entry : metricValues.entrySet()) {
            if (entry.getValue() > highestValue) {
                highestValue = entry.getValue();
                worstMetric = entry.getKey();
            }
        }

        return worstMetric;
    }

}
