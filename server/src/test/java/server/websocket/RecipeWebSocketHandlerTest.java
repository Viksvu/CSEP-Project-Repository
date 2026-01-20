package server.websocket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

class RecipeWebSocketHandlerTest {

    private RecipeWebSocketHandler handler;
    private WebSocketSession session1;
    private WebSocketSession session2;

    @BeforeEach
    void setUp() {
        handler = new RecipeWebSocketHandler();

        session1 = mock(WebSocketSession.class);
        session2 = mock(WebSocketSession.class);

        when(session1.getId()).thenReturn("s1");
        when(session2.getId()).thenReturn("s2");
        when(session1.isOpen()).thenReturn(true);
        when(session2.isOpen()).thenReturn(true);
    }

    @Test
    void afterConnectionEstablished_doesNotThrow() {
        handler.afterConnectionEstablished(session1);
        try {
            verify(session1, never()).sendMessage(any());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    void handleTextMessage_setsCurrentRecipe_andReceivesUpdate() throws Exception {
        handler.afterConnectionEstablished(session1);

        handler.handleTextMessage(
                session1,
                new TextMessage("VIEW_RECIPE:42")
        );

        handler.notifyRecipeUpdated(42);

        verify(session1).sendMessage(new TextMessage("RECIPE_UPDATED:42"));
    }

    @Test
    void notifyRecipeUpdated_onlyNotifiesSessionsViewingThatRecipe() throws Exception {
        handler.afterConnectionEstablished(session1);
        handler.afterConnectionEstablished(session2);

        handler.handleTextMessage(session1, new TextMessage("VIEW_RECIPE:1"));
        handler.handleTextMessage(session2, new TextMessage("VIEW_RECIPE:2"));

        handler.notifyRecipeUpdated(1);

        verify(session1).sendMessage(new TextMessage("RECIPE_UPDATED:1"));
        verify(session2, never()).sendMessage(any());
    }

    @Test
    void notifyRecipeDeleted_notifiesAllSessions() throws Exception {
        handler.afterConnectionEstablished(session1);
        handler.afterConnectionEstablished(session2);

        handler.notifyRecipeDeleted(99);

        verify(session1).sendMessage(new TextMessage("RECIPE_DELETED:99"));
        verify(session2).sendMessage(new TextMessage("RECIPE_DELETED:99"));
    }

    @Test
    void notifyRecipeAdded_notifiesAllSessions() throws Exception {
        handler.afterConnectionEstablished(session1);
        handler.afterConnectionEstablished(session2);

        handler.notifyRecipeAdded(77);

        verify(session1).sendMessage(new TextMessage("RECIPE_ADDED:77"));
        verify(session2).sendMessage(new TextMessage("RECIPE_ADDED:77"));
    }

    @Test
    void afterConnectionClosed_removesSession_andStopsNotifications() throws Exception {
        handler.afterConnectionEstablished(session1);
        handler.afterConnectionClosed(session1, CloseStatus.NORMAL);

        handler.notifyRecipeAdded(1);

        verify(session1, never()).sendMessage(any());
    }
}
