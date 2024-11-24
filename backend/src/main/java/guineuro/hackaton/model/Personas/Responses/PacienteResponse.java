package guineuro.hackaton.model.Personas.Responses;

public class PacienteResponse {
    private Integer id;
    private String nombre;
    private String telefonoEmergencia;
    private String telefonoPaciente;

    public PacienteResponse(Integer _id, String _nombre, String _tlfEmergencia, String _telefonoPaciente) {
        this.id = _id;
        this.nombre = _nombre;
        this.telefonoEmergencia = _tlfEmergencia;
        this.telefonoPaciente = _telefonoPaciente;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefonoEmergencia() {
        return telefonoEmergencia;
    }

    public void setTelefonoEmergencia(String telefonoEmergencia) {
        this.telefonoEmergencia = telefonoEmergencia;
    }

    public String getTelefonoPaciente() {
        return telefonoPaciente;
    }

    public void setTelefonoPaciente(String telefonoPaciente) {
        this.telefonoPaciente = telefonoPaciente;
    }
}
