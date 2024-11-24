package guineuro.hackaton.utils;

import guineuro.hackaton.model.Cuestionarios.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestCSVReader {

    public List<Test> leerTestsDesdeCSV(String rutaCSV) {
        List<Test> tests = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV, StandardCharsets.UTF_8))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",", -1); // Divide por comas

                if (valores.length >= 3) {
                    try {
                        // Parsear los valores del CSV
                        Integer idTest = Integer.parseInt(valores[0].trim());
                        String nombreTest = valores[1].trim();
                        List<Integer> preguntasTest = new ArrayList<>();

                        // Parsear IDs de preguntas
                        for (int i = 2; i < valores.length; i++) {
                            Integer idPregunta = Integer.parseInt(valores[i].trim());
                            preguntasTest.add(idPregunta);
                        }

                        // Crear un nuevo Test (implementación de la clase concreta)
                        Test test = new Test(idTest, nombreTest, preguntasTest) {
                            // Clase anónima para implementar Test, ya que es abstracta
                        };
                        tests.add(test);

                    } catch (NumberFormatException e) {
                        System.err.println("Error al convertir un ID: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }

        return tests;
    }
}
