package server.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws")
@Component
public class RecipeWebSocket {

    // Keep track of all connected clients
    private static final Set<Session> sessions =
            ConcurrentHashMap.newKeySet();
    private static final ConcurrentHashMap<Session, Long> currentRecipe =
            new ConcurrentHashMap<>();

    /**
     * On open of the session
     * @param session the session.
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("WebSocket client connected: " + session.getId());
    }

    /**
     * On the close of session
     * @param session the session
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("WebSocket client disconnected: " + session.getId());
    }

    /**
     * When sub message is sent
     * @param message the message
     * @param session the session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received WS message: " + message);

        if(message.startsWith("VIEW_RECIPE:")){
            long recipeId=Long.parseLong(message.split(":")[1]);
            currentRecipe.put(session, recipeId);
        }
    }

    /**
     * Notify all connected clients that a recipe has changed
     * @param recipeId the id of a recipe.
     */
    public static void notifyRecipeUpdated(long recipeId) {
        String msg = "RECIPE_UPDATED:" + recipeId;
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(msg);
            }
        }
    }
}
