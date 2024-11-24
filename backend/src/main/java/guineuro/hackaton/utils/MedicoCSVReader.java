package guineuro.hackaton.utils;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import guineuro.hackaton.model.Personas.Medico;

public class MedicoCSVReader {

    public List<Medico> leerMedicosDesdeCSV(String rutaCSV) {
        List<Medico> medicos = new ArrayList<>();
        String linea;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCSV))) {
            // Leer línea por línea
            while ((linea = br.readLine()) != null) {
                // Dividir los valores por coma
                String[] valores = linea.split(",");

                // Parsear los datos
                Integer id = Integer.parseInt(valores[0].trim());
                String nombre = valores[1].trim();
                String telefonoMedico = valores[2].trim();

                // Lista de pacientes (los IDs de pacientes vienen desde la posición 3 en adelante)
                List<Integer> listaPacientes = new ArrayList<>();
                for (int i = 3; i < valores.length; i++) {
                    listaPacientes.add(Integer.parseInt(valores[i].trim()));
                }

                // Crear objeto Medico
                Medico medico = new Medico(id, nombre, telefonoMedico, listaPacientes);

                // Agregar a la lista de médicos
                medicos.add(medico);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear un valor numérico: " + e.getMessage());
        }

        return medicos;
    }

    /**
     * Añadir un paciente al médico correspondiente en el archivo CSV.
     *
     * @param rutaCSV    Ruta del archivo CSV.
     * @param idMedico   ID del médico al que se le asignará el paciente.
     * @param idPaciente ID del nuevo paciente a añadir.
     * @return true si se añadió el paciente correctamente, false en caso de error.
     */
    public boolean addPacienteAMedicoEnCSV(String rutaCSV, Integer idMedico, Integer idPaciente) {
        List<String> lineas = new ArrayList<>();
        boolean medicoEncontrado = false;

        try {
            // Leer todas las líneas del archivo CSV
            lineas = Files.readAllLines(Paths.get(rutaCSV));

            // Crear una lista para las nuevas líneas
            List<String> nuevasLineas = new ArrayList<>();

            for (String linea : lineas) {
                String[] valores = linea.split(",");

                // Si la línea corresponde al médico
                if (Integer.parseInt(valores[0].trim()) == idMedico) {
                    medicoEncontrado = true;

                    // Comprobar si el paciente ya está en la lista
                    List<String> idsPacientes = Arrays.asList(Arrays.copyOfRange(valores, 3, valores.length));
                    if (!idsPacientes.contains(idPaciente.toString())) {
                        linea += "," + idPaciente; // Añadir el nuevo ID de paciente
                    }
                }

                // Añadir la línea (modificada o no) a la lista nueva
                nuevasLineas.add(linea);
            }

            if (!medicoEncontrado) {
                System.err.println("Médico con ID " + idMedico + " no encontrado en el archivo.");
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

        /**
     * Eliminar un paciente del médico correspondiente en el archivo CSV.
     *
     * @param rutaCSV    Ruta del archivo CSV.
     * @param idMedico   ID del médico al que se le quitará el paciente.
     * @param idPaciente ID del paciente a eliminar.
     * @return true si se eliminó el paciente correctamente, false en caso de error.
     */
    public boolean removePacienteDeMedicoEnCSV(String rutaCSV, Integer idMedico, Integer idPaciente) {
        List<String> lineas = new ArrayList<>();
        boolean medicoEncontrado = false;

        try {
            // Leer todas las líneas del archivo CSV
            lineas = Files.readAllLines(Paths.get(rutaCSV));

            // Crear una lista para las nuevas líneas
            List<String> nuevasLineas = new ArrayList<>();

            for (String linea : lineas) {
                String[] valores = linea.split(",");

                // Si la línea corresponde al médico
                if (Integer.parseInt(valores[0].trim()) == idMedico) {
                    medicoEncontrado = true;

                    // Remover el ID del paciente si está en la lista
                    List<String> idsPacientes = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(valores, 3, valores.length)));
                    idsPacientes.remove(idPaciente.toString());

                    // Reconstruir la línea sin el paciente eliminado
                    String nuevaLinea = String.join(",", Arrays.copyOf(valores, 3)) + "," + String.join(",", idsPacientes);
                    nuevaLinea = nuevaLinea.endsWith(",") ? nuevaLinea.substring(0, nuevaLinea.length() - 1) : nuevaLinea;

                    nuevasLineas.add(nuevaLinea);
                } else {
                    // Si no es el médico buscado, agregar la línea tal cual
                    nuevasLineas.add(linea);
                }
            }

            if (!medicoEncontrado) {
                System.err.println("Médico con ID " + idMedico + " no encontrado en el archivo.");
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
}
