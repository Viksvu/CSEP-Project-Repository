package server.websocket;

import org.junit.jupiter.api.Test;
import org.springframework.context.event.ContextClosedEvent;

import static org.mockito.Mockito.*;

class WebSocketShutdownListenerTest {

    @Test
    void onContextClosed_shouldNotifyHandler() {
        RecipeWebSocketHandler handler = mock(RecipeWebSocketHandler.class);
        WebSocketShutdownListener listener =
                new WebSocketShutdownListener(handler);

        ContextClosedEvent event = mock(ContextClosedEvent.class);

        listener.onContextClosed(event);

        verify(handler).notifyServerShutdown();
    }
}
