package server.services;

import org.springframework.stereotype.Service;
import server.websocket.RecipeWebSocketHandler;

@Service
public class RecipeSocketService {

    private final RecipeWebSocketHandler socketHandler;

    /**
     * Creates a recipe
     * socket service
     * @param socketHandler
     */
    public RecipeSocketService(RecipeWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    /**
     * notifier to socket
     * that some recipe
     * has been updated
     * @param recipeId
     */
    public void recipeUpdated(long recipeId) {
        socketHandler.notifyRecipeUpdated(recipeId);
    }
}
