package guineuro.hackaton.model.Personas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CambioMedicoRequest {
    @JsonProperty("ID_paciente")
    private Integer ID_paciente;
    
    @JsonProperty("ID_medico")
    private Integer ID_medico;

    CambioMedicoRequest() {

    }

    CambioMedicoRequest(Integer ID_paciente, Integer ID_medico) {
        this.ID_paciente = ID_paciente;
        this.ID_medico = ID_medico;
    }
    
    // Getters y Setters
    public Integer getID_paciente() {
        return ID_paciente;
    }

    public void setID_paciente(Integer ID_paciente) {
        this.ID_paciente = ID_paciente;
    }

    public Integer getID_medico() {
        return ID_medico;
    }

    public void setID_medico(Integer ID_medico) {
        this.ID_medico = ID_medico;
    }
}
