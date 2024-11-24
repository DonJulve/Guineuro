package guineuro.hackaton.model.Cuestionarios;

import java.util.HashMap;
import java.util.Map;

public class Respuesta {
    private Integer idPaciente;
    private Integer idTest;
    private Map<Integer, Integer> respuestas; //Map que relaciona el id de pregunta con la puntuación

    public Respuesta() {
        this.respuestas = new HashMap<>();
    }

    public Respuesta(Integer idPaciente, Integer idTest, Map<Integer, Integer> respuestas) {
        this.idPaciente = idPaciente;
        this.idTest = idTest;
        this.respuestas = respuestas;
    }

    public Integer getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Integer getIdTest() {
        return idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public Map<Integer, Integer> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(Map<Integer, Integer> respuestas) {
        this.respuestas = respuestas;
    }
}
