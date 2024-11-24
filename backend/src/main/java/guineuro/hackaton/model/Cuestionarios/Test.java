package guineuro.hackaton.model.Cuestionarios;

import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    private Integer idTest;
    private String nombre;
    private List<Integer> preguntas = new ArrayList<>();

    public Test() {}
    
    public Test(Integer idTest, String nombre, List<Integer> preguntas) {
        this.idTest = idTest;
        this.nombre = nombre;
        this.preguntas = preguntas;
    }

    public Integer getIdTest() {
        return this.idTest;
    }

    public void setIdTest(Integer idTest) {
        this.idTest = idTest;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Integer> getPreguntas() {
        return this.preguntas;
    }

    public void addPregunta(Integer pregunta) {
        this.preguntas.add(pregunta);
    }
}
