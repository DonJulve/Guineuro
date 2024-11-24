package guineuro.hackaton.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import guineuro.hackaton.model.Metricas;

public class MetricasCSVReader {

    public List<Metricas> leerMetricasDesdeCSV(String rutaCSV) {
        List<Metricas> listaMetricas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            String linea;

            // Leer línea por línea
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split(",");
                if (valores.length != 7) {
                    System.err.println("Línea inválida: " + linea);
                    continue;
                }

                // Parsear los valores
                Integer timestamp = Integer.parseInt(valores[0].trim());
                Float delusion = Float.parseFloat(valores[1].trim());
                Float alucinaciones = Float.parseFloat(valores[2].trim());
                Float hablaIncoherente = Float.parseFloat(valores[3].trim());
                Float movimientoInusual = Float.parseFloat(valores[4].trim());
                Float socializacion = Float.parseFloat(valores[5].trim());
                Float higiene = Float.parseFloat(valores[6].trim());

                // Crear objeto Metricas y agregarlo a la lista
                Metricas metricas = new Metricas(timestamp, delusion, alucinaciones, hablaIncoherente, movimientoInusual, socializacion, higiene);
                listaMetricas.add(metricas);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un número: " + e.getMessage());
        }

        return listaMetricas;
    }

    public void escribirMetricas(String rutaDirectorio, Integer idPaciente, Metricas nuevasMetricas) {
        String rutaArchivo = rutaDirectorio + "/" + idPaciente + ".csv";
        int numeroDeTest = 0;

        // Leer el número del último test del archivo existente (si existe)
        if (Files.exists(Paths.get(rutaArchivo))) {
            try {
                List<String> lineas = Files.readAllLines(Paths.get(rutaArchivo));
                if (!lineas.isEmpty()) {
                    String ultimaLinea = lineas.get(lineas.size() - 1);
                    String[] valores = ultimaLinea.split(",");
                    numeroDeTest = Integer.parseInt(valores[0].trim()) + 1; // Incrementar el número de test
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error al leer el archivo existente: " + e.getMessage());
            }
        }

        // Crear la nueva línea con las métricas
        String nuevaLinea = numeroDeTest + "," +
                            nuevasMetricas.getDelusion() + "," +
                            nuevasMetricas.getAlucinaciones() + "," +
                            nuevasMetricas.getHablaIncoherente() + "," +
                            nuevasMetricas.getMovimientoInusual() + "," +
                            nuevasMetricas.getSocializacion() + "," +
                            nuevasMetricas.getHigiene();

        // Escribir la nueva línea en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo, true))) {
            writer.write(nuevaLinea);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
