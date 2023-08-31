package ru.netology;

import ru.netology.utils.PropertyLoader;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.Integer.*;

public class TestRunnableClientTester implements Runnable {
    private Socket socket;
    private final int clientNumber;

    public TestRunnableClientTester(int clientNumber) {
        this.clientNumber = clientNumber;
        try {

            socket = new Socket(PropertyLoader.getProperty("server.host"), parseInt(PropertyLoader.getProperty("server.port")));
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream())) {
            oos.writeUTF("Alex-" + clientNumber);

            int i = 0;
            while (i < 5) {
                oos.writeUTF("Всем приветы! " + i);
                oos.flush();
                Thread.sleep(5000);
                i++;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
