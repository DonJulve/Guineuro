import React, { useState } from "react";
import axios from "axios";
import "./GuineuroChat.css"; // Opcional: archivo CSS para estilos

const GuineuroChat = () => {
  // Estados para manejar el mensaje del usuario y la conversación
  const [userMessage, setUserMessage] = useState("");
  const [chatMessages, setChatMessages] = useState([]);
  const [loading, setLoading] = useState(false);

  // Manejar envío del mensaje
  const handleSendMessage = async () => {
    if (userMessage.trim() === "") return; // Evitar mensajes vacíos

    // Añadir mensaje del usuario al chat
    setChatMessages((prevMessages) => [
      ...prevMessages,
      { sender: "user", text: userMessage },
    ]);

    // Limpiar el campo de entrada y activar el estado de carga
    setUserMessage("");
    setLoading(true);

    try {
      // Enviar el mensaje al servidor del chatbot
      const response = await axios.post("http://localhost:8080/guineuro/respond", {
        message: userMessage,
      });

      // Añadir respuesta del chatbot al chat
      setChatMessages((prevMessages) => [
        ...prevMessages,
        { sender: "bot", text: response.data.response },
      ]);
    } catch (error) {
      // Manejar errores
      setChatMessages((prevMessages) => [
        ...prevMessages,
        { sender: "bot", text: "Lo siento, ocurrió un error al procesar tu mensaje." },
      ]);
    } finally {
      setLoading(false);
    }
  };

  // Manejar el evento de presionar "Enter" en el input
  const handleKeyPress = (e) => {
    if (e.key === "Enter") {
      handleSendMessage();
    }
  };

  return (
    <div className="guineuro-chat-container">
      <div className="chat-header">
        <h2>Chat con Guineuro</h2>
      </div>
      <div className="chat-messages">
        {chatMessages.map((message, index) => (
          <div
            key={index}
            className={`chat-message ${
              message.sender === "user" ? "user-message" : "bot-message"
            }`}
          >
            {message.text}
          </div>
        ))}
        {loading && <div className="chat-message bot-message">Escribiendo...</div>}
      </div>
      <div className="chat-input-container">
        <input
          type="text"
          placeholder="Escribe tu mensaje..."
          value={userMessage}
          onChange={(e) => setUserMessage(e.target.value)}
          onKeyPress={handleKeyPress}
        />
        <button onClick={handleSendMessage} disabled={loading}>
          Enviar
        </button>
      </div>
    </div>
  );
};

export default GuineuroChat;
