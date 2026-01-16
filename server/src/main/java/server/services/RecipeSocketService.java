package server.services;

import org.springframework.stereotype.Service;
import server.websocket.RecipeWebSocketHandler;

@Service
public class RecipeSocketService {

    private final RecipeWebSocketHandler socketHandler;

    public RecipeSocketService(RecipeWebSocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public void recipeUpdated(long recipeId) {
        socketHandler.notifyRecipeUpdated(recipeId);
    }
}
