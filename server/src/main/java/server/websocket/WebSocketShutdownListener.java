package server.websocket;

import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WebSocketShutdownListener {

    private final RecipeWebSocketHandler handler;

    /**
     * Constructor for this handler
     * @param handler
     */
    public WebSocketShutdownListener(RecipeWebSocketHandler handler) {
        this.handler = handler;
    }

    /**
     * On the event of closing
     * it send a message.
     * @param event
     */
    @EventListener
    public void onContextClosed(ContextClosedEvent event) {
        handler.notifyServerShutdown();
        System.out.println(">>> CONTEXT CLOSED EVENT FIRED <<<");
        try {
            Thread.sleep(500); // allow WS flush
        } catch (InterruptedException ignored) {}
    }
}
