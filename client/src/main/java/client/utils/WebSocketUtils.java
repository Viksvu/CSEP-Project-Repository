package client.utils;

import jakarta.websocket.*;

import java.net.URI;
import java.util.function.Consumer;

@ClientEndpoint
public class WebSocketUtils {

    private Session session;
    private Consumer<String> messageHandler;

    /**
     * Constructor for a web socket
     * api controller
     * @param messageHandler the handler of web
     *                       sockets messages
     */
    public WebSocketUtils(Consumer<String> messageHandler){
        this.messageHandler=messageHandler;
    }


    /**
     * Setter for session handler
     * @param messageHandler the handler.
     */
    public void setMessageHandler(Consumer<String> messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * connects to server via given
     * URL
     */
    public void connect(){
        try {
            WebSocketContainer container =
                    ContainerProvider.getWebSocketContainer();

            container.connectToServer(
                    this,
                    URI.create("ws://localhost:8080/ws")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs on open of the web
     * socket
     * @param session the active
     *                session
     */
    @OnOpen
    public void opOpen(Session session){
        this.session = session;
        System.out.println("WebSocket connected");
    }

    /**
     * How messages are handled
     * from the server
     * @param message the message from server
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("WebSocket message: " + message);
        messageHandler.accept(message);
    }

    /**
     * What happens on close.
     * Session is set to null
     * @param session the session with server.
     */
    @OnClose
    public void onClose(Session session) {
        this.session = null;
        System.out.println("WebSocket disconnected");
    }

    /**
     * Send what we are currently
     * subscribed too.
     * @param message the sub messages
     */
    public void send(String message) {
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }




}
