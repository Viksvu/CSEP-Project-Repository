package client.utils;

import client.commonsClient.ConfigHolder;
import com.google.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.function.Consumer;

@Component
public class WebSocketUtils extends TextWebSocketHandler {

    private volatile WebSocketSession session;
    private volatile Consumer<String> messageCollector;

    private final ConfigHolder config;

    /**
     * Constructor for new injectable websocket utils instance
     * @param config injected
     */
    @Inject
    public WebSocketUtils(ConfigHolder config) {
        this.config = config;
    }

    /**
     * Connect to the WebSocket endpoint
     */
    public void connect(Consumer<String> messageCollector) {
        this.messageCollector = messageCollector;

        StandardWebSocketClient client = new StandardWebSocketClient();
        client.doHandshake(this, config.get().getSocketIp());
    }

    /**
     * Send a message to the server
     */
    public void send(String message) {
        WebSocketSession s = this.session;
        if (s != null && s.isOpen()) {
            try {
                s.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                throw new IllegalStateException("Failed to send WebSocket message", e);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        System.out.println("WebSocket connected");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        Consumer<String> collector = this.messageCollector;
        if (collector != null) {
             collector.accept(message.getPayload());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.session = null;
        System.out.println("WebSocket disconnected");
    }
}
