package client.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class WebSocketUtils {

    private WebSocketSession session;
    private WebSocketMessageHandler handler;



    /**
     * Connect to the server endpoint
     */
    public void connect() {
        handler.setClient(this);
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(handler, "ws://localhost:8080/ws");
    }

    public void setHandler(WebSocketMessageHandler handler) {
        this.handler = handler;
    }

    /**
     * Set the current session
     *
     * @param session
     */
    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    /**
     * Send a message to the endpoint
      * @param message
     */
    public void send(String message) {
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
