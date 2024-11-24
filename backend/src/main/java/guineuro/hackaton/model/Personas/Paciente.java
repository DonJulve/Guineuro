package guineuro.hackaton.model.Personas;

import guineuro.hackaton.model.Metricas;

public class Paciente {
    private Integer id; 
    private Integer idMedico;
    private String nombre;
    private Metricas metricas;
    private String telefonoPaciente;
    private String telefonoEmergencia;
    // private String chatIDContactoEmergencia;

    public Paciente() {

    }
    
    public Paciente(Integer id, Integer idMedico, String nombre, Metricas metricas, String telefonoPaciente, String telefonoEmergencia) {
        this.id = id;
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.metricas = metricas;
        this.telefonoPaciente = telefonoPaciente;
        this.telefonoEmergencia = telefonoEmergencia;
        //CREAR CHATID CONTACTO EMERGENCIA
    }

    public Paciente(Integer id, Integer idMedico, String nombre) {
        this.id = id;
        this.idMedico = idMedico;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Metricas getMetricas() {
        return metricas;
    }

    public void setMetricas(Metricas metricas) {
        this.metricas = metricas;
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

    public Float getSquizoPoints() {
        return this.metricas.getSquizoPoints();
    }

    public String getWorstMetric() {
        return this.metricas.getWorstMetric();
    }
}
