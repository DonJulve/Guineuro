package guineuro.hackaton.utils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;

public class TelegramBot {

    private static final String BOT_TOKEN = "7749755908:AAGeOTQ5yQ_JB5JYCvQLGgme6-vrtQxFEV8";
    private static final String TELEGRAM_API_URL = "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";
    private static final String CHAT_ID = "6504720948";

    /**
     * Enviar un mensaje a Telegram.
     *
     * @param chatId  ID del chat (usuario o grupo).
     * @param mensaje Mensaje a enviar.
     */
    public void enviarMensaje(String mensaje) {
        String encodedMessage="";
        try {
            encodedMessage = URLEncoder.encode(mensaje, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            System.err.println("[!] Hay algun caracter q da problemas");
        }
        
        try {
            // Crear la URL con los parámetros
            String url = TELEGRAM_API_URL + "?chat_id=" + CHAT_ID + "&text=" + encodedMessage;

            // Crear la solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            // Enviar la solicitud
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Mostrar el resultado
            System.out.println("Estado: " + response.statusCode());
            System.out.println("Respuesta: " + response.body());
        } catch (Exception e) {
            System.err.println("Error al enviar mensaje: " + e.getMessage());
        }
    }
}
