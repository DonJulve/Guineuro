package guineuro.hackaton.model.Cuestionarios;

public class Pregunta {
    private Integer id;
    private String texto;
    private String metrica;

    public Pregunta(Integer id, String texto, String metrica) {
        this.id = id;
        this.texto = texto;
        this.metrica = metrica;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getMetrica() {
        return this.metrica;
    }

    public void setMetrica(String metrica) {
        this.metrica = metrica;
    }
}
