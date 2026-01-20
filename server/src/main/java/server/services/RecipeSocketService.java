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

    /**
     * notifier to socket
     * that some recipe
     * has been deleted
     * @param recipeId
     */
    public void recipeDeleted(long recipeId){
        socketHandler.notifyRecipeDeleted(recipeId);
    }


    /**
     * notifier to socket
     * that the list view needs
     * to add a recipe
     * @param recipeId
     */
    public void recipeAdded(long recipeId){
        socketHandler.notifyRecipeAdded(recipeId);
    }
}
