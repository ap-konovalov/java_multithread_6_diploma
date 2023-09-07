import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.Server;
import ru.netology.utils.PropertyLoader;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

public class ServerTests {

    private Socket pingSocket = null;
    private final PrintWriter out = null;
    private static final int SERVER_PORT = Integer.parseInt(PropertyLoader.getProperty("server.port"));
    private static final String SERVER_HOST = PropertyLoader.getProperty("server.host");
    private PrintWriter pingSocketOut;
    private BufferedReader pingSocketIn;

    @BeforeEach
    void init() {
        new Thread(() -> {
            try {
                Server.main(new String[]{});
            } catch (IOException ignored) {
            }
        }).start();
    }

    @AfterEach
    void tearDown() {
        if (out != null) {
            out.close();
        }
        try {
            if (pingSocketIn != null) {
                pingSocketIn.close();
            }
            if (pingSocketOut != null) {
                pingSocketOut.close();
            }
            pingSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldAcceptConnection() {
        try {
            pingSocket = new Socket(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(pingSocket.isConnected());
    }

    @Test
    void shouldAcceptMultipleConnections() {
        try {
            pingSocket = new Socket(SERVER_HOST, SERVER_PORT);
            Socket secondSocket = new Socket(SERVER_HOST, SERVER_PORT);
            assertTrue(pingSocket.isConnected());
            assertTrue(secondSocket.isConnected());
            secondSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldReceiveMessagesFromClients() {
        try {
            pingSocket = new Socket(SERVER_HOST, SERVER_PORT);
            pingSocketIn = new BufferedReader(new InputStreamReader(pingSocket.getInputStream()));
            pingSocketOut = new PrintWriter(pingSocket.getOutputStream(), true);
            pingSocketOut.println("Alex");
            pingSocketIn.readLine();
            pingSocketIn.readLine();
            Socket secondSocket = new Socket(SERVER_HOST, SERVER_PORT);
            PrintWriter secondOut = new PrintWriter(secondSocket.getOutputStream(), true);
            secondOut.println("Den");
            String secondUserExpectedMessage = "Test message";
            secondOut.println(secondUserExpectedMessage);
            assertEquals(secondUserExpectedMessage, pingSocketIn.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
