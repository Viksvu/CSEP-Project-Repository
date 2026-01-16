package client.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private WebSocketUtils client;

    public void setClient(WebSocketUtils client) {
        this.client = client;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        client.setSession(session);
        System.out.println("WebSocket connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        System.out.println("Received: " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        client.setSession(null);
        System.out.println("WebSocket disconnected");
    }
}
