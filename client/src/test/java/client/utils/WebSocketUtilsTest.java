package client.utils;

import client.commonsClient.ConfigHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class WebSocketUtilsTest {

    private WebSocketUtils utils;
    private TestWebSocketSession session;

    @BeforeEach
    void setUp() {
        utils = new WebSocketUtils(new ConfigHolder());
        session = new TestWebSocketSession();
    }

    @Test
    void afterConnectionEstablished_setsSession_andAllowsSend() {
        utils.afterConnectionEstablished(session);

        utils.send("HELLO");

        assertEquals(List.of("HELLO"), session.sentMessages);
    }

    @Test
    void send_doesNothingWhenSessionIsClosed() {
        session.open = false;
        utils.afterConnectionEstablished(session);

        utils.send("TEST");

        assertTrue(session.sentMessages.isEmpty());
    }

    @Test
    void handleTextMessage_passesPayloadToConsumer() {
        AtomicReference<String> received = new AtomicReference<>();

        Consumer<String> consumer = received::set;
        utils.connect(consumer);

        utils.handleTextMessage(
                session,
                new TextMessage("FROM_SERVER")
        );

        assertEquals("FROM_SERVER", received.get());
    }

    @Test
    void handleTextMessage_doesNothingWithoutConsumer() {
        assertDoesNotThrow(() ->
                utils.handleTextMessage(
                        session,
                        new TextMessage("IGNORED")
                )
        );
    }

    @Test
    void afterConnectionClosed_clearsSession() {
        utils.afterConnectionEstablished(session);
        utils.afterConnectionClosed(session, CloseStatus.NORMAL);

        utils.send("NOPE");

        assertTrue(session.sentMessages.isEmpty());
    }

    @Test
    void send_wrapsIOExceptionInIllegalStateException() {
        session.throwOnSend = true;
        utils.afterConnectionEstablished(session);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> utils.send("FAIL")
        );

        assertTrue(ex.getMessage().contains("Failed to send WebSocket message"));
    }

    /**
     * Manual WebSocketSession stub (Spring 6 / Java 25 compatible)
     */
    static class TestWebSocketSession implements WebSocketSession {

        boolean open = true;
        boolean throwOnSend = false;
        List<String> sentMessages = new ArrayList<>();

        private int textLimit = 8192;
        private int binaryLimit = 8192;

        @Override
        public boolean isOpen() {
            return open;
        }

        @Override
        public void sendMessage(WebSocketMessage<?> message) throws IOException {
            if (throwOnSend) {
                throw new IOException("boom");
            }
            sentMessages.add(((TextMessage) message).getPayload());
        }
        @Override
        public int getTextMessageSizeLimit() {
            return textLimit;
        }

        @Override
        public void setTextMessageSizeLimit(int messageSizeLimit) {
            this.textLimit = messageSizeLimit;
        }

        @Override
        public int getBinaryMessageSizeLimit() {
            return binaryLimit;
        }

        @Override
        public void setBinaryMessageSizeLimit(int messageSizeLimit) {
            this.binaryLimit = messageSizeLimit;
        }

        @Override
        public List<WebSocketExtension> getExtensions() {
            return List.of();
        }

        @Override public String getId() { return "test"; }
        @Override public URI getUri() { return null; }
        @Override public HttpHeaders getHandshakeHeaders() { return HttpHeaders.EMPTY; }
        @Override public Map<String, Object> getAttributes() { return Map.of(); }
        @Override public Principal getPrincipal() { return null; }
        @Override public InetSocketAddress getLocalAddress() { return null; }
        @Override public InetSocketAddress getRemoteAddress() { return null; }
        @Override public String getAcceptedProtocol() { return null; }
        @Override public void close() {}
        @Override public void close(CloseStatus status) {}
    }
}
