package guineuro.hackaton.endpoints;

import guineuro.hackaton.model.Metricas;
import guineuro.hackaton.model.SystemInfo;
import guineuro.hackaton.model.Personas.CambioMedicoRequest;
import guineuro.hackaton.model.Personas.Medico;
import guineuro.hackaton.model.Personas.Paciente;
import guineuro.hackaton.model.Personas.Responses.MedicoResponse;
import guineuro.hackaton.model.Personas.Responses.PacienteResponse;
import guineuro.hackaton.model.Cuestionarios.Pregunta;
import guineuro.hackaton.model.Cuestionarios.Respuesta;
import guineuro.hackaton.model.Cuestionarios.Responses.TestResponse;
import guineuro.hackaton.usecases.MedicoUseCases;
import guineuro.hackaton.usecases.PacienteUseCases;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @CrossOrigin(origins = "http://localhost:3000/", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
public class AppController {

    private SystemInfo systemInfo = new SystemInfo();
    MedicoUseCases medicoUseCases;
    PacienteUseCases pacienteUseCases;

    AppController() {
        // CARGAR LA INFORMACION DE LOS CSV
        String pathActual = System.getProperty("user.dir");
        systemInfo.retrieveMedicosFromCSV(pathActual + "/src/main/java/guineuro/hackaton/CSV/Medicos.csv");
        systemInfo.retrievePacientesFromCSV(pathActual + "/src/main/java/guineuro/hackaton/CSV/Pacientes.csv");
        systemInfo.retrieveTestsFromCSV(pathActual + "/src/main/java/guineuro/hackaton/CSV/Tests/Tests.csv");
        systemInfo.retrievePreguntasFromCSV(pathActual + "/src/main/java/guineuro/hackaton/CSV/Tests/Preguntas.csv");

        medicoUseCases = new MedicoUseCases(systemInfo);
        pacienteUseCases = new PacienteUseCases(systemInfo);
    }

    /****** ENDPOINTS PARA PACIENTES ******/

    @GetMapping("/paciente/fotos/pacientes/{ID}")
    public byte[] getFotoPaciente(@PathVariable Integer ID) {
        return pacienteUseCases.getFotoPaciente(ID);
    }
    
    @GetMapping("/paciente/fotos/medico/{ID}")
    public byte[] getFotoMedicoPaciente(@PathVariable Integer ID) {
        return pacienteUseCases.getFotoMedico(ID);
    }

    @GetMapping("/paciente/info/medico/{ID}")
    public MedicoResponse getInfoMedicoResponse(@PathVariable Integer ID) {
        return pacienteUseCases.obtenerInformacionMedico(ID);
    }

    @GetMapping("/paciente/info/paciente/{ID}")
    public PacienteResponse getPacienteResponse(@PathVariable Integer ID) {
        return pacienteUseCases.obtenerPacienteResponse(ID);
    }

    @GetMapping("/paciente/login/{nombre}")
    public Integer getIDPaciente(@PathVariable String nombre) {
        return pacienteUseCases.getID(nombre);
    }
    
    @GetMapping("/paciente/test/{ID}")
    public TestResponse getTest(@PathVariable Integer ID) {
        return pacienteUseCases.getTest(ID);
    }

    @PostMapping("/paciente/respuesta")
    public void respuestaPaciente(@RequestBody Respuesta respuesta) {
         pacienteUseCases.gestionarRespuesta(respuesta);
    }   
    
    @GetMapping("/paciente/botonPanico/{ID}")
    public String botonPanico(@PathVariable Integer ID) {
        return pacienteUseCases.activarBotonPanico(ID);
    }
    
    /******* ENDPOINTS PARA MEDICOS *******/
    @PostMapping("/medico/enviarMensaje")
    public String enviarMensaje(@RequestBody String mensaje) {
        return medicoUseCases.enviarMensaje(mensaje);
    }

    @PostMapping("/medico/cambiarMedico")
    public String cambiarMedico(@RequestBody CambioMedicoRequest cambioMedicoRequest) {
        if (medicoUseCases.cambiarMedicoPaciente(cambioMedicoRequest)) {
            return "Paciente cambiado de medico exitosamente!";
        }
        return "Error al cambiar el paciente de medico";
    }
    
    @PostMapping("/medico/register")
    public String registrarPaciente(@RequestBody Paciente paciente) {
        if(medicoUseCases.registrarPaciente(paciente)){
            return "Paciente registrado correctamente!";
        }
        return "Error en el registro...";
    }
    
    @DeleteMapping("/medico/borrarPaciente/{userID}")
    public String borrarPaciente(@PathVariable Integer userID) {
         if (medicoUseCases.quitarPaciente(userID)) {
            return "Usuario borrado exitosamente";
         }
         return "Error al borrar el paciente...";
    }

    @GetMapping("/medico/login/{nombre}")
    public Integer getIDMedico(@PathVariable String nombre) {
        return medicoUseCases.getID(nombre);
    }
    
    @GetMapping("/medico/fotos/{ID}")
    public byte[] getFoto(@PathVariable Integer ID) {
        return medicoUseCases.getFoto(ID);
    }

    @GetMapping("/medico/pacientes/{ID}")  
    public List<PacienteResponse> getListaPacientes(@PathVariable Integer ID) {
        return medicoUseCases.obtenerListaPacientes(ID);
    }

    @GetMapping("/medico/metricas/{ID}")
    public List<Metricas> getMetricas(@PathVariable Integer ID) {
        return medicoUseCases.getMetricas(ID);
    }
    
    @GetMapping("/medico/info/paciente/{ID}")
    public Paciente getInfoPaciente(@PathVariable Integer ID) {
        return medicoUseCases.obtenerPaciente(ID);
    }
    
    @GetMapping("/medico/info/diagnosticoInicial/{ID}")
    public Metricas getMethodName(@PathVariable Integer ID) {
        return medicoUseCases.getDiagnosticoInicial(ID);
    }
    
    /***** ENDPOINTS DE PRUEBA ******/
    @GetMapping("/info/medicos/{medicoID}")
    public Medico getInfoMedico(@PathVariable Integer medicoID) {
        return systemInfo.getMedico(medicoID);
    }
    
    @GetMapping("/info/pacientes/all")
    public List<Paciente> getAllPacientes() {
        return systemInfo.getAllPacientes();
    }

    @GetMapping("/info/medicos/all")
    public List<Medico> getAllMedicos() {
        return systemInfo.getAllMedicos();
    }

    @GetMapping("/info/preguntas/all")
    public List<Pregunta> getAllPreguntas() {
        return systemInfo.getAllPreguntas();
    }
    
}
