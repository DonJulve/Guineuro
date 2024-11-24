package guineuro.hackaton.usecases;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import guineuro.hackaton.model.Metricas;
import guineuro.hackaton.model.SystemInfo;
import guineuro.hackaton.model.Personas.Medico;
import guineuro.hackaton.model.Personas.Paciente;
import guineuro.hackaton.model.Cuestionarios.Test;
import guineuro.hackaton.model.Cuestionarios.Pregunta;
import guineuro.hackaton.model.Cuestionarios.Respuesta;
import guineuro.hackaton.model.Personas.Responses.MedicoResponse;
import guineuro.hackaton.model.Personas.Responses.PacienteResponse;
import guineuro.hackaton.utils.MetricasCSVReader;
import guineuro.hackaton.utils.PacienteCSVReader;
import guineuro.hackaton.utils.TelegramBot;
import guineuro.hackaton.model.Cuestionarios.Responses.TestResponse;

public class PacienteUseCases {
    private SystemInfo systemInfo;



    // Constructor que recibe SystemInfo para acceder a los datos
    public PacienteUseCases(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }
    public Integer getID(String nombre) {
        for(Paciente paciente : systemInfo.getAllPacientes()) {
            if(paciente.getNombre().equals(nombre)){
                return paciente.getId();
            }
        }
        return -1;
    }

    //GET INFORMACION PACIENTE Y MEDICO¡
    public MedicoResponse obtenerInformacionMedico(Integer idPaciente) {
        Paciente paciente = systemInfo.getPaciente(idPaciente);
        if (paciente == null) {
            System.err.println("[!] Paciente" + idPaciente + " no encontrado.");
            return null;
        }

        Medico medico = systemInfo.getMedico(paciente.getIdMedico());
        if (medico == null) {
            System.err.println("[!] No se encontró un médico asignado al paciente: " + idPaciente );
            return null;
        }

        return new MedicoResponse(medico.getNombre(), medico.getTelefonoMedico());
    }
    public byte[] obtenerMetricas(Integer idPaciente) {
    // Construir la ruta al archivo
    String filePath = System.getProperty("user.dir") + "/src/main/java/guineuro/hackaton/CSV/MetricasPacientes/" + idPaciente + ".csv";
    byte[] fileBytes = null;

    try {
        // Leer los datos del archivo
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("El archivo no existe: " + filePath);
        }
        fileBytes = Files.readAllBytes(Paths.get(filePath));
    } catch (IOException e) {
        System.err.println("[ERROR] No se pudo leer el archivo: " + e.getMessage());
    }

    // Devolver los datos como array de bytes
    return fileBytes;
    }
    public PacienteResponse obtenerPacienteResponse(Integer idPaciente) {
        Paciente paciente = systemInfo.getPaciente(idPaciente);
        if (paciente == null) {
            System.err.println("[!] Paciente" + idPaciente + " no encontrado.");
            return null;
        }

        return new PacienteResponse(paciente.getId(), paciente.getNombre(), paciente.getTelefonoEmergencia(), paciente.getTelefonoPaciente());
    }

    //GET FOTOS
    public byte[] getFotoPaciente(Integer idPaciente) {
        // Ruta de la imagen (puede ser del classpath, disco, etc.)
        String imgPath = System.getProperty("user.dir") + "/src/main/java/guineuro/hackaton/fotos/" + idPaciente + ".jpg";
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
    public byte[] getFotoMedico(Integer idPaciente) {
        Integer medicoID = systemInfo.getPaciente(idPaciente).getIdMedico();
        // Ruta de la imagen (puede ser del classpath, disco, etc.)
        String imgPath = System.getProperty("user.dir") + "/src/main/java/guineuro/hackaton/fotos/" + medicoID + ".jpg";
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

   // GET TEST
    public TestResponse getTest(Integer idPaciente) {
        Paciente paciente = systemInfo.getPaciente(idPaciente);
        Integer idTest;
        if (paciente.getSquizoPoints() == 0.0f) {
            idTest = 2; // Test Diagnóstico General si no se han hecho tests
        } else {
            switch (paciente.getWorstMetric()) {
                case "delusion":
                    idTest = 5; // Test Enfoque en Delirios
                    break;
                case "alucinaciones":
                    idTest = 6; // Test Enfoque en Alucinaciones
                    break;
                case "hablaIncoherente":
                    idTest = 7; // Test Enfoque en Habla Incoherente
                    break;
                case "movimientoInusual":
                    idTest = 8; // Test Enfoque en Movimiento Inusual
                    break;
                case "socializacion":
                    idTest = 4; // Test Enfoque en Socialización
                    break;
                case "higiene":
                    idTest = 3; // Test Enfoque en Higiene
                    break;
                default:
                    idTest = 1; // Por defecto, Test Diagnóstico Corto
                    break;
            }
        }

        Test test = systemInfo.getTest(idTest); // Supongo que este método obtiene el test con su lista de IDs

        // Crear el objeto TestResponse
        TestResponse testResponse = new TestResponse();
        testResponse.setIdTest(idTest);
        testResponse.setNombre(test.getNombre());

        // Obtener los textos de las preguntas del test
        List<Pregunta> preguntas = new ArrayList<>();
        for (Integer idPregunta : test.getPreguntas()) { // Obtener la lista de IDs de preguntas
            Pregunta pregunta = systemInfo.getPregunta(idPregunta); // Suponemos este método obtiene la pregunta por ID
            if (pregunta != null) {
                preguntas.add(pregunta); // Añadir el texto de la pregunta a la lista
            }
        }

        // Establecer la lista de textos de preguntas en el TestResponse
        testResponse.setPreguntas(preguntas);

        return testResponse;
    }

    public void gestionarRespuesta(Respuesta respuesta) {
        MetricasCSVReader metricasCSVReader = new MetricasCSVReader();
        Metricas oldMetricas = systemInfo.getPaciente(respuesta.getIdPaciente()).getMetricas();

        String pathActual = System.getProperty("user.dir");
        String pathMetricas = pathActual + "/src/main/java/guineuro/hackaton/CSV/MetricasPacientes";

        Metricas newMetricas = new Metricas(oldMetricas);

        // Procesar las respuestas
        for (Entry<Integer, Integer> entry : respuesta.getRespuestas().entrySet()) {
            Integer idPregunta = entry.getKey();
            Integer puntuacion = entry.getValue();

            // Obtener la pregunta asociada
            Pregunta pregunta = systemInfo.getPregunta(idPregunta);
            if (pregunta == null) {
                continue;
            }

            // Determinar la métrica asociada a la pregunta
            String metrica = pregunta.getMetrica();

            // Actualizar la métrica correspondiente
            switch (metrica) {
                case "delusion":
                    newMetricas.setDelusion(actualizarPuntuacion(oldMetricas.getDelusion(), puntuacion));
                    break;
                case "alucinaciones":
                    newMetricas.setAlucinaciones(actualizarPuntuacion(oldMetricas.getAlucinaciones(), puntuacion));
                    break;
                case "hablaIncoherente":
                    newMetricas.setHablaIncoherente(actualizarPuntuacion(oldMetricas.getHablaIncoherente(), puntuacion));
                    break;
                case "movimientoInusual":
                    newMetricas.setMovimientoInusual(actualizarPuntuacion(oldMetricas.getMovimientoInusual(), puntuacion));
                    break;
                case "socializacion":
                    newMetricas.setSocializacion(actualizarPuntuacion(oldMetricas.getSocializacion(), puntuacion));
                    break;
                case "higiene":
                    newMetricas.setHigiene(actualizarPuntuacion(oldMetricas.getHigiene(), puntuacion));
                    break;
                default:
                    break;
            }
        }

        // Guardar las métricas nuevas en el archivo de métricas
        metricasCSVReader.escribirMetricas(pathMetricas, respuesta.getIdPaciente(), newMetricas);

        // Actualizar las métricas en el sistema
        Paciente paciente = systemInfo.getPaciente(respuesta.getIdPaciente());
        paciente.setMetricas(newMetricas);

        // Actualizar el archivo de pacientes
        PacienteCSVReader pacienteCSVReader = new PacienteCSVReader();
        String pathPacientes = pathActual + "/src/main/java/guineuro/hackaton/CSV/Pacientes.csv";
        pacienteCSVReader.actualizarMetricasPaciente(pathPacientes, paciente.getId(), newMetricas);
    }

    // Método auxiliar para actualizar una métrica según las reglas de puntuación
    private Float actualizarPuntuacion(Float metricaActual, Integer puntuacionRespuesta) {
        switch (puntuacionRespuesta) {
            case 1: // Resta 2
                return Math.max(0, metricaActual - 5); // Asegura que no baje de 0
            case 2: // Resta 1
                return Math.max(0, metricaActual - 2); // Asegura que no baje de 0
            case 3: // No hace nada
                return metricaActual;
            case 4: // Suma 1
                return Math.min(100, metricaActual + 2); // Asegura que no supere 100
            case 5: // Suma 2
                return Math.min(100, metricaActual + 5); // Asegura que no supere 100
            default:
                throw new IllegalArgumentException("Puntuación fuera del rango permitido (1-5): " + puntuacionRespuesta);
        }
    }

    public String activarBotonPanico(Integer idPaciente) {
        Paciente paciente = systemInfo.getPaciente(idPaciente);
        if (paciente == null) {
            return "Paciente no encontrado.";
        }
        TelegramBot bot = new TelegramBot();
        bot.enviarMensaje("🚨El paciente " + systemInfo.getPaciente(idPaciente).getNombre()
                            + " con id: " + systemInfo.getPaciente(idPaciente).getId() 
                            + " ha pulsado el botón de pánico 🚨\n "
                            + "Número de teléfono del paciente: " + systemInfo.getPaciente(idPaciente).getTelefonoPaciente()
                            + "\nNúmero de teléfono del contacto de emergencia: " + systemInfo.getPaciente(idPaciente).getTelefonoEmergencia());
        return "¡Botón de pánico activado para " + paciente.getNombre() + "! Notificación enviada.";
    }
}
