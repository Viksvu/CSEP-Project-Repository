package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.websocket.RecipeWebSocketHandler;

import static org.mockito.Mockito.*;

class RecipeSocketServiceTest {

    private RecipeWebSocketHandler handler;
    private RecipeSocketService service;

    @BeforeEach
    void setUp() {
        handler = mock(RecipeWebSocketHandler.class);
        service = new RecipeSocketService(handler);
    }

    @Test
    void recipeUpdated_delegatesToHandler() {
        service.recipeUpdated(10L);

        verify(handler).notifyRecipeUpdated(10L);
    }

    @Test
    void recipeDeleted_delegatesToHandler() {
        service.recipeDeleted(20L);

        verify(handler).notifyRecipeDeleted(20L);
    }

    @Test
    void recipeAdded_delegatesToHandler() {
        service.recipeAdded(30L);

        verify(handler).notifyRecipeAdded(30L);
    }
}
