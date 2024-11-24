package guineuro.hackaton.model.Personas;

import java.util.ArrayList;
import java.util.List;

public class Medico {
    private Integer id;
    private String nombre;
    private String telefonoMedico;
    // private String chatIDPacientes; //TODO TELEGRAM?
    private List<Integer> listaPacientes = new ArrayList<>();

    public Medico(Integer id, String nombre, String telefonoMedico, List<Integer> listaPacientes) {
        this.id = id;
        this.nombre = nombre;
        this.telefonoMedico = telefonoMedico;
        this.listaPacientes = listaPacientes;
        //CREAR CHATID
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

    public List<Integer> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<Integer> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public String getTelefonoMedico() {
        return telefonoMedico;
    }

    public void setTelefonoMedico(String telefonoMedico) {
        this.telefonoMedico = telefonoMedico;
    }

    /**
     * Agregar un paciente a la lista de pacientes.
     * 
     * @param IntegerPaciente Integer del paciente a agregar.
     * @return true si el paciente fue agregado, false si ya existía.
     */
    public boolean addPaciente(Integer IntegerPaciente) {
        if (!listaPacientes.contains(IntegerPaciente)) {
            listaPacientes.add(IntegerPaciente);
            return true; // Paciente agregado
        }
        return false; // Paciente ya estaba en la lista
    }

    /**
     * Eliminar un paciente de la lista de pacientes.
     * 
     * @param IntegerPaciente Integer del paciente a eliminar.
     * @return true si el paciente fue eliminado, false si no se encontró en la lista.
     */
    public boolean removePaciente(Integer IntegerPaciente) {
        return listaPacientes.remove(IntegerPaciente);
    }
}
