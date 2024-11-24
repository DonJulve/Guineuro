package guineuro.hackaton.utils;

import guineuro.hackaton.model.Metricas;
import guineuro.hackaton.model.Personas.Paciente;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PacienteCSVReader {

    public List<Paciente> leerPacientesDesdeCSV(String rutaCSV) {
        List<Paciente> pacientes = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            // Leer línea por línea
            while ((linea = br.readLine()) != null) {
                // Dividir los valores por coma
                String[] valores = linea.split(",");

                // Parsear los datos
                Integer id = Integer.parseInt(valores[0].trim());
                Integer idMedico = Integer.parseInt(valores[1].trim());
                String nombre = valores[2].trim();
                Float delusion = Float.parseFloat(valores[3].trim());
                Float alucinaciones = Float.parseFloat(valores[4].trim());
                Float hablaIncoherente = Float.parseFloat(valores[5].trim());
                Float movimientoInusual = Float.parseFloat(valores[6].trim());
                Float socializacion = Float.parseFloat(valores[7].trim());
                Float higiene = Float.parseFloat(valores[8].trim());
                String telefonoPaciente = valores[9].trim();
                String telefonoEmergencia = valores[10].trim();

                // Crear objeto Metricas
                Metricas metricas = new Metricas(delusion, alucinaciones, hablaIncoherente, movimientoInusual, socializacion, higiene);

                // Crear objeto Paciente
                Paciente paciente = new Paciente(id, idMedico, nombre, metricas, telefonoPaciente, telefonoEmergencia);

                // Agregar a la lista de pacientes
                pacientes.add(paciente);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un valor numérico: " + e.getMessage());
        }
        return pacientes;
    }

    public Integer addPacienteCSV(String rutaCSV, Integer idMedico, String nombre, Metricas metricas, String telefonoPaciente, String telefonoEmergencia) {
        Integer nuevoId = 1; // ID inicial si el archivo está vacío
        List<String> lineas = new ArrayList<>();

        try {
            // Leer todas las líneas del archivo CSV
            if (Files.exists(Paths.get(rutaCSV))) {
                lineas = Files.readAllLines(Paths.get(rutaCSV));

                // Si hay datos, obtener el último ID
                if (!lineas.isEmpty()) {
                    String ultimaLinea = lineas.get(lineas.size() - 1);
                    String[] campos = ultimaLinea.split(",");
                    nuevoId = Integer.parseInt(campos[0]) + 1; // Incrementar el último ID
                }
            }

            // Crear la nueva línea con los datos del paciente
            String nuevaLinea = nuevoId + "," + 
                                idMedico + "," + 
                                nombre + "," + 
                                metricas.getDelusion() + "," + 
                                metricas.getAlucinaciones() + "," + 
                                metricas.getHablaIncoherente() + "," + 
                                metricas.getMovimientoInusual() + "," + 
                                metricas.getSocializacion() + "," + 
                                metricas.getHigiene() + "," + 
                                telefonoPaciente + "," + 
                                telefonoEmergencia;

            // Agregar la nueva línea al archivo CSV
            Files.write(Paths.get(rutaCSV), Collections.singletonList(nuevaLinea), StandardOpenOption.APPEND, StandardOpenOption.CREATE);

        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
            return null;
        }

        return nuevoId; // Devuelve el nuevo ID generado
    }

    /**
     * Eliminar una línea completa de un paciente basado en su ID del archivo CSV.
     *
     * @param rutaCSV Ruta del archivo CSV.
     * @param idPaciente ID del paciente a eliminar.
     * @return true si se eliminó correctamente, false si hubo un error.
     */
    public boolean borrarPacientePorID(String rutaCSV, Integer idPaciente) {
        List<String> lineas = new ArrayList<>();
        boolean pacienteEncontrado = false;

        try {
            // Leer todas las líneas del archivo CSV
            lineas = Files.readAllLines(Paths.get(rutaCSV));

            // Crear una lista para las nuevas líneas (sin el paciente a eliminar)
            List<String> nuevasLineas = new ArrayList<>();

            for (String linea : lineas) {
                String[] valores = linea.split(",");
                Integer idLinea = Integer.parseInt(valores[0].trim());

                if (!idLinea.equals(idPaciente)) {
                    // Mantener las líneas que no coincidan con el ID
                    nuevasLineas.add(linea);
                } else {
                    pacienteEncontrado = true;
                }
            }

            if (!pacienteEncontrado) {
                System.err.println("Paciente con ID " + idPaciente + " no encontrado en el archivo.");
                return false;
            }

            // Escribir las nuevas líneas en el archivo (sobrescribir)
            Files.write(Paths.get(rutaCSV), nuevasLineas, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Error al procesar el archivo CSV: " + e.getMessage());
            return false;
        }
        return true;
    }

    public boolean actualizarMetricasPaciente(String rutaCSV, Integer idPaciente, Metricas nuevasMetricas) {
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;

        try {
            // Leer todas las líneas del archivo CSV
            if (Files.exists(Paths.get(rutaCSV))) {
                lineas = Files.readAllLines(Paths.get(rutaCSV));
            } else {
                System.err.println("El archivo CSV no existe: " + rutaCSV);
                return false;
            }

            // Crear una lista para las nuevas líneas
            List<String> nuevasLineas = new ArrayList<>();

            // Procesar cada línea
            for (String linea : lineas) {
                String[] valores = linea.split(",");
                Integer idActual = Integer.parseInt(valores[0].trim());

                if (idActual.equals(idPaciente)) {
                    // Modificar las métricas del paciente encontrado
                    valores[3] = nuevasMetricas.getDelusion().toString();
                    valores[4] = nuevasMetricas.getAlucinaciones().toString();
                    valores[5] = nuevasMetricas.getHablaIncoherente().toString();
                    valores[6] = nuevasMetricas.getMovimientoInusual().toString();
                    valores[7] = nuevasMetricas.getSocializacion().toString();
                    valores[8] = nuevasMetricas.getHigiene().toString();
                    encontrado = true;
                }

                // Reconstruir la línea actualizada
                nuevasLineas.add(String.join(",", valores));
            }

            // Escribir las nuevas líneas al archivo
            Files.write(Paths.get(rutaCSV), nuevasLineas, StandardOpenOption.TRUNCATE_EXISTING);

            if (encontrado) {
                System.out.println("Métricas del paciente ID " + idPaciente + " actualizadas correctamente.");
            } else {
                System.err.println("Paciente con ID " + idPaciente + " no encontrado.");
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo CSV: " + e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un ID en el archivo CSV: " + e.getMessage());
            return false;
        }

        return encontrado;
    }

}
