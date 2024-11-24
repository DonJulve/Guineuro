package guineuro.hackaton.model.Cuestionarios.Responses;

import java.util.List;

import guineuro.hackaton.model.Cuestionarios.Pregunta;

public class TestResponse {
    private Integer idTest;
    private String nombre;
    private List<Pregunta> preguntas;

    public TestResponse() {
        
    }

    public TestResponse(Integer idTest, String nombre, List<Pregunta> preguntas) {
        this.idTest = idTest;
        this.nombre = nombre;
        this.preguntas = preguntas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getIdTest() {
        return idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }
}
