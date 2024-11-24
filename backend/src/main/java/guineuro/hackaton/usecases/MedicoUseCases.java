package guineuro.hackaton.usecases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import guineuro.hackaton.model.Metricas;
import guineuro.hackaton.model.SystemInfo;
import guineuro.hackaton.model.Personas.CambioMedicoRequest;
import guineuro.hackaton.model.Personas.Medico;
import guineuro.hackaton.model.Personas.Paciente;
import guineuro.hackaton.model.Personas.Responses.PacienteResponse;
import guineuro.hackaton.utils.MedicoCSVReader;
import guineuro.hackaton.utils.MetricasCSVReader;
import guineuro.hackaton.utils.PacienteCSVReader;
import guineuro.hackaton.utils.TelegramBot;


public class MedicoUseCases {
    private SystemInfo systemInfo;

    // Constructor que recibe SystemInfo para acceder a los datos
    public MedicoUseCases(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;

    }

    public Integer getID(String nombre) {
        for(Medico medico : systemInfo.getAllMedicos()) {
            if(medico.getNombre().equals(nombre)){
                return medico.getId();
            }
        }
        return -1;
    }
    
    public byte[] getFoto(Integer id) {
        // Ruta de la imagen (puede ser del classpath, disco, etc.)
        String imgPath = System.getProperty("user.dir") + "/src/main/java/guineuro/hackaton/fotos/" + id + ".jpg";
        String defaultImgPath = System.getProperty("user.dir") + "/src/main/java/guineuro/hackaton/fotos/default.jpg";
        // Leer los bytes de la imagen
        byte[] imageBytes = null;
        try {
            imageBytes = Files.readAllBytes(Paths.get(imgPath));
        } catch (Exception e) {
            System.err.println("[!] no ha encontrado la foto!!");
        }
        try {
            imageBytes = Files.readAllBytes(Paths.get(defaultImgPath));
        }
        catch (Exception e) {
            System.err.println("Poniendo foto deafult");
        }
        return imageBytes;
    }


    public Paciente obtenerPaciente(Integer id_paciente) {
        return systemInfo.getPaciente(id_paciente);
    }

    public List<PacienteResponse>obtenerListaPacientes(Integer id_medico) {
        List<PacienteResponse> listaPacienteResponse = new ArrayList<>();
        Medico medico = systemInfo.getMedico(id_medico);
        for(Paciente p: systemInfo.getAllPacientes()) {
            if(medico.getId() == p.getIdMedico()) {
                listaPacienteResponse.add(new PacienteResponse(p.getId(), p.getNombre(), p.getTelefonoEmergencia(), p.getTelefonoPaciente()));
            }
        } 
        return listaPacienteResponse;
    }

    public boolean registrarPaciente(Paciente paciente) {
        Medico medico = systemInfo.getMedico(paciente.getIdMedico());
        if (medico != null) {
            String pathActual = System.getProperty("user.dir");
            String path_medicos = pathActual + "/src/main/java/guineuro/hackaton/CSV/Medicos.csv";
            String path_pacientes = pathActual + "/src/main/java/guineuro/hackaton/CSV/Pacientes.csv";
            PacienteCSVReader pacienteReader = new PacienteCSVReader();
            Integer id_paciente = pacienteReader.addPacienteCSV(path_pacientes, paciente.getIdMedico(), 
                    paciente.getNombre(), paciente.getMetricas(), paciente.getTelefonoPaciente(), paciente.getTelefonoPaciente());
            paciente.setId(id_paciente);
            MedicoCSVReader medicoCSVReader = new MedicoCSVReader();
            medicoCSVReader.addPacienteAMedicoEnCSV(path_medicos, paciente.getIdMedico(), paciente.getId());
            if (medico.addPaciente(paciente.getId())) {
                paciente.setIdMedico(paciente.getIdMedico());
                systemInfo.addPaciente(paciente);
                return true;
            }
        }
        return false;
    }

    public boolean quitarPaciente(Integer id_paciente) {
        // Obtener el paciente y el médico asociado
        Paciente paciente = systemInfo.getPaciente(id_paciente);
        if (paciente == null) {
            System.err.println("Paciente con ID " + id_paciente + " no encontrado.");
            return false; // Paciente no existe
        }

        Medico medico = systemInfo.getMedico(paciente.getIdMedico());
        if (medico == null) {
            System.err.println("Médico asociado al paciente con ID " + id_paciente + " no encontrado.");
            return false; // Médico no existe
        }

        // Rutas a los archivos CSV
        String pathActual = System.getProperty("user.dir");
        String pathMedicos = pathActual + "/src/main/java/guineuro/hackaton/CSV/Medicos.csv";
        String pathPacientes = pathActual + "/src/main/java/guineuro/hackaton/CSV/Pacientes.csv";

        // Instanciar lectores CSV
        PacienteCSVReader pacienteReader = new PacienteCSVReader();
        MedicoCSVReader medicoCSVReader = new MedicoCSVReader();

        // Eliminar al paciente de la lista del médico en el archivo CSV
        boolean eliminadoDeMedicoCSV = medicoCSVReader.removePacienteDeMedicoEnCSV(pathMedicos, medico.getId(), id_paciente);
        if (!eliminadoDeMedicoCSV) {
            System.err.println("Error al eliminar al paciente con ID " + id_paciente + " del archivo Medicos.csv.");
            return false; // No se pudo eliminar del archivo Medicos.csv
        }

        // Eliminar al paciente del archivo Pacientes.csv
        boolean eliminadoDePacientesCSV = pacienteReader.borrarPacientePorID(pathPacientes, id_paciente);
        if (!eliminadoDePacientesCSV) {
            System.err.println("Error al eliminar al paciente con ID " + id_paciente + " del archivo Pacientes.csv.");
            return false; // No se pudo eliminar del archivo Pacientes.csv
        }

        // Eliminar al paciente del médico en memoria
        if (!medico.removePaciente(id_paciente)) {
            System.err.println("Error al eliminar al paciente con ID " + id_paciente + " del médico en memoria.");
            return false; // No se pudo eliminar en memoria
        }

        // Eliminar al paciente de la lista global en memoria
        if (!systemInfo.getAllPacientes().remove(paciente)) {
            System.err.println("Error al eliminar al paciente con ID " + id_paciente + " de la lista global en memoria.");
            return false; // No se pudo eliminar de la lista global en memoria
        }

        System.out.println("Paciente con ID " + id_paciente + " eliminado correctamente.");
        return true;
    }


    public boolean cambiarMedicoPaciente(CambioMedicoRequest cambioMedicoRequest) {
        Integer id_medico = cambioMedicoRequest.getID_medico();
        Integer id_paciente = cambioMedicoRequest.getID_paciente();
        System.out.println("[DEBUG] id_paciente: " + id_paciente + " id_medico: " + id_medico);
        Paciente paciente = systemInfo.getPaciente(id_paciente);
        Medico nuevoMedico = systemInfo.getMedico(id_medico);
        System.out.println("[DEBUG] nuevoMedico: " + nuevoMedico.getNombre());
        if (paciente != null && nuevoMedico != null) {
            Medico medicoActual = systemInfo.getMedico(paciente.getIdMedico());
            System.out.println("[DEBUG] medicoactual: " + medicoActual.getNombre());
            if (medicoActual != null) {
                medicoActual.removePaciente(id_paciente);
            }
            nuevoMedico.addPaciente(id_paciente);
            paciente.setIdMedico(id_medico);
            return true;
        }
        return false;
    }

    public List<Metricas> getMetricas(Integer ID) {
        MetricasCSVReader csvReader = new MetricasCSVReader();
        String pathActual = System.getProperty("user.dir");
        String pathMetricas = pathActual + "/src/main/java/guineuro/hackaton/CSV/MetricasPacientes/"+ ID +".csv";
        return csvReader.leerMetricasDesdeCSV(pathMetricas);
    }

    public Metricas getDiagnosticoInicial(Integer ID) {
        List<Metricas> allMetricas = getMetricas(ID);
        return allMetricas.get(0);
    }

    public String enviarMensaje(String mensaje) {
        TelegramBot bot = new TelegramBot();
        bot.enviarMensaje(mensaje);
        return "mensaje enviado! (deberia)";
    }
}
