package client.scenes;
import static org.mockito.Mockito.*;

import client.utils.WebSocketUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainCtrlTest {

    private MainCtrl sut;
    private WebSocketUtils ws;

    @BeforeEach
    void setup() {
        ws = mock(WebSocketUtils.class);
        sut = new MainCtrl(ws);
    }

    @Test
    void sendToWSEndpoint_sendsCorrectMessage() {
        sut.sendToWSEndpoint(42L);

        verify(ws).send("VIEW_UPDATE:42");
    }
}
