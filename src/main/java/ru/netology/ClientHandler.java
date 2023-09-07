package ru.netology;

import ru.netology.utils.Logger;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private PrintWriter out;
    private final Logger logger;
    private BufferedReader in;

    public ClientHandler(Socket client, Logger logger) throws IOException {
        this.socket = client;
        this.logger = logger;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        start();
    }

    @Override
    public void run() throws NullPointerException {
        try {
            String clientName = in.readLine();
            out.println("User '" + clientName + "' has joined!");
            out.println("Now you can write messages!");
            String entry;
            while (true) {
                entry = in.readLine();

                if (entry.equals("/exit")) {
                    stopService();
                    break;
                }

                for (ClientHandler client : Server.clients) {
                    if (!client.equals(this)) {
                        client.send(entry);
                    }
                }

                logger.log(entry);
            }
        } catch (IOException e) {
            stopService();
        }
    }

    private void send(String msg) {
        out.println(msg);
    }

    private void stopService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
                for (ClientHandler client : Server.clients) {
                    if (client.equals(this)) {
                        client.interrupt();
                        Server.clients.remove(this);
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
