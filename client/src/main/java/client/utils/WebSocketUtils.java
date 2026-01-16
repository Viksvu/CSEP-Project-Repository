package client.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Component
public class WebSocketUtils {

    private WebSocketSession session;
    private final WebSocketMessageHandler handler;

    public WebSocketUtils(WebSocketMessageHandler handler) {
        this.handler = handler;
    }

    public void connect() {
        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(handler, "ws://localhost:8080/ws");
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
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
