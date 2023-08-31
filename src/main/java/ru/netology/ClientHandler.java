package ru.netology;

import lombok.SneakyThrows;
import ru.netology.utils.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private DataOutputStream out;
    private final Logger logger;

    public ClientHandler(Socket client, Logger logger) {
        socket = client;
        this.logger = logger;
    }

    @SneakyThrows
    @Override
    public void run() {
        out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        out.writeUTF("Whats your name?");
        String clientName = in.readUTF();
        System.out.println("User '" + clientName + "' has joined!");

        while (!socket.isClosed()) {
            String entry = in.readUTF();

            if (entry.equalsIgnoreCase("/exit")) {
                break;
            }

            for (ClientHandler clients : Server.clients) {
                clients.out.writeUTF(clientName + ": " + entry);
                out.flush();
            }

            logger.log(clientName, entry);
        }

        in.close();
        out.close();
        socket.close();
    }
}
