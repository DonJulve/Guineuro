package guineuro.hackaton.utils;

import guineuro.hackaton.model.Cuestionarios.Pregunta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PreguntaCSVReader {

    public List<Pregunta> leerPreguntasDesdeCSV(String rutaCSV) {
        List<Pregunta> preguntas = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV, StandardCharsets.UTF_8))) {

            // Leer cada línea restante
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",", -1); // Divide por comas

                if (valores.length >= 3) {
                    try {
                        // Parsear los valores
                        Integer id = Integer.parseInt(valores[0].trim());
                        String metrica = valores[1].trim();

                        // Quitar comillas del campo texto
                        String texto = valores[2].trim();
                        if (texto.startsWith("\"") && texto.endsWith("\"")) {
                            texto = texto.substring(1, texto.length() - 1);
                        }

                        // Crear una instancia de Pregunta
                        Pregunta pregunta = new Pregunta(id, texto, metrica);
                        preguntas.add(pregunta);

                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir el ID a entero: " + valores[0]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return preguntas;
    }
}
