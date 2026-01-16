package server.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RecipeWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions =
            ConcurrentHashMap.newKeySet();

    private final Map<WebSocketSession, Long> currentRecipe =
            new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("WS connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("Received WS message: " + payload);

        if (payload.startsWith("VIEW_RECIPE:")) {
            long recipeId = Long.parseLong(payload.split(":")[1]);
            currentRecipe.put(session, recipeId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        currentRecipe.remove(session);
        System.out.println("WS disconnected: " + session.getId());
    }

    /** Notify all clients */
    public void notifyRecipeUpdated(long recipeId) {
        TextMessage msg = new TextMessage("RECIPE_UPDATED:" + recipeId);

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
