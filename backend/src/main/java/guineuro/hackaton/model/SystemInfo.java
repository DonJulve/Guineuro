package guineuro.hackaton.model;

import guineuro.hackaton.model.Personas.Medico;
import guineuro.hackaton.model.Personas.Paciente;
import guineuro.hackaton.model.Cuestionarios.Test;
import guineuro.hackaton.model.Cuestionarios.Pregunta;
import guineuro.hackaton.utils.MedicoCSVReader;
import guineuro.hackaton.utils.PacienteCSVReader;
import guineuro.hackaton.utils.PreguntaCSVReader;
import guineuro.hackaton.utils.TestCSVReader;
import java.util.ArrayList;
import java.util.List;

public class SystemInfo {
    private List<Medico> medicos = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Pregunta> preguntas = new ArrayList<>();
    private List<Test> tests = new ArrayList<>();

    public boolean addPaciente(Paciente paciente) {
        if (!pacientes.contains(paciente)) {
            pacientes.add(paciente);
            return true; // Paciente agregado
        }
        return false; // Paciente ya estaba en la lista
    }

    public boolean addMedico(Medico medico) {
        if (!medicos.contains(medico)) {
            medicos.add(medico);
            return true; // Paciente agregado
        }
        return false; // Paciente ya estaba en la lista
    }

    public boolean addPregunta(Pregunta pregunta) {
        if (!preguntas.contains(pregunta)) {
            preguntas.add(pregunta);
            return true; // Pregunta agregado
        }
        return false; // Pregunta ya estaba en la lista
    }

    public boolean addTest(Test test) {
        if (!tests.contains(test)) {
            tests.add(test);
            return true; // Test agregado
        }
        return false; // Test ya estaba en la lista
    }

    public boolean removePaciente(Paciente paciente) {
        // Verificar si el paciente está en la lista global
        if (pacientes.contains(paciente)) {
            // Eliminar al paciente de la lista global
            pacientes.remove(paciente);

            // Buscar al médico asociado y eliminar al paciente de su lista
            Integer idMedico = paciente.getIdMedico();
            for (Medico medico : medicos) {
                if (medico.getId().equals(idMedico)) {
                    medico.removePaciente(paciente.getId());
                    break;
                }
            }

            return true; // Paciente eliminado correctamente
        }
        return false; // Paciente no estaba en la lista
    }

    /* PUBLIC FUNCTIONS */
    public Medico getMedico(Integer uuid_medico) {
        for (Medico medico : medicos) {
            if (uuid_medico == medico.getId()) {
                return medico;
            }
        }
        return null;
    }

    public Paciente getPaciente(Integer uuid_paciente) {
        for (Paciente paciente : pacientes) {
            if (uuid_paciente == paciente.getId()) {
                return paciente;
            }
        }
        return null;
    }

    public Pregunta getPregunta(Integer idPregunta) {
        for (Pregunta Pregunta : preguntas) {
            if (idPregunta == Pregunta.getId()) {
                return Pregunta;
            }
        }
        return null;
    }

    public Test getTest(Integer idTest) {
        for (Test Test : tests) {
            if (idTest == Test.getIdTest()) {
                return Test;
            }
        }
        return null;
    }

    public void changeMedico(Integer idPaciente, Integer idMedicoNuevo) {
        //ACTUALIZAMOS EL MEDICO DEL PACIENTE
        Integer idMedicoViejo = -1;
        for (Paciente paciente : pacientes) {
            if (idPaciente == paciente.getId()) {
                idMedicoViejo = paciente.getIdMedico();
                paciente.setIdMedico(idMedicoNuevo);
                break;
            }
        }
        if (idMedicoViejo > -1) {
            //AÑADIMOS AL MEDICO NUEVO, EL PACIENTE QUE SE CAMBIA DE MEDICO Y LO QUITAMOS DEL MEDICO VIEJO 
            for (Medico medico: medicos) {
                if (medico.getId() == idMedicoNuevo) {
                    medico.addPaciente(idPaciente);
                } else if (medico.getId() == idMedicoViejo){
                    medico.removePaciente(idPaciente);
                }
            }
        } 
    }

    public List<Medico> getAllMedicos() {
        return medicos;
    }

    public List<Paciente> getAllPacientes() {
        return pacientes;
    }
    
    /**
     * Lee los pacientes desde un archivo CSV y los agrega a la lista de pacientes.
     */
    public void retrievePacientesFromCSV(String rutaCSV) {
        PacienteCSVReader pacienteReader = new PacienteCSVReader();
        List<Paciente> pacientesDesdeCSV = pacienteReader.leerPacientesDesdeCSV(rutaCSV);
        for (Paciente paciente : pacientesDesdeCSV) {
            addPaciente(paciente);
        }
    }

    /**
     * Lee los médicos desde un archivo CSV y los agrega a la lista de médicos.
     */
    public void retrieveMedicosFromCSV(String rutaCSV) {
        MedicoCSVReader medicoReader = new MedicoCSVReader();
        List<Medico> medicosDesdeCSV = medicoReader.leerMedicosDesdeCSV(rutaCSV);
        for (Medico medico : medicosDesdeCSV) {
            addMedico(medico);
        }
    }

    /**
     * Lee los preguntas desde un archivo CSV y los agrega a la lista de preguntas.
     */
    public void retrievePreguntasFromCSV(String rutaCSV) {
        PreguntaCSVReader PreguntaReader = new PreguntaCSVReader();
        List<Pregunta> PreguntasDesdeCSV = PreguntaReader.leerPreguntasDesdeCSV(rutaCSV);
        for (Pregunta Pregunta : PreguntasDesdeCSV) {
            addPregunta(Pregunta);
        }
    }

    /**
     * Lee los tests desde un archivo CSV y los agrega a la lista de tests.
     */
    public void retrieveTestsFromCSV(String rutaCSV) {
        TestCSVReader TestReader = new TestCSVReader();
        List<Test> TestsDesdeCSV = TestReader.leerTestsDesdeCSV(rutaCSV);
        for (Test Test : TestsDesdeCSV) {
            addTest(Test);
        }
    }

    public List<Pregunta> getAllPreguntas(){
        return preguntas;
    }
}
