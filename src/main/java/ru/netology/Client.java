package ru.netology;

import lombok.SneakyThrows;
import ru.netology.utils.PropertyLoader;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner scanner;
    private String clientName;


    public Client() {
        try {
            socket = new Socket(PropertyLoader.getProperty("server.host"), parseInt(PropertyLoader.getProperty("server.port")));
            scanner = new Scanner(System.in);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            this.setName();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        new SendMessage().start();
        new ReceiveMessage().start();
    }

    public class SendMessage extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            String msg;
            while (true) {
                msg = scanner.nextLine();
                if (msg.equals("/exit")) {
                    Client.this.stopService();
                    break;
                } else {
                    out.println(clientName + ": " + msg);
                }
            }
        }
    }

    private class ReceiveMessage extends Thread {
        @SneakyThrows
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = in.readLine();
                    if (msg.equals("/exit")) {
                        Client.this.stopService();
                        break;
                    }
                    System.out.println(msg);
                }
            } catch (IOException ex) {
                Client.this.stopService();
            }
        }
    }

    private void stopService() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            out.close();
            in.close();
        }
    }

    private void setName() {
        System.out.println("Whats your name?");
        clientName = scanner.nextLine();
        out.println(clientName);
    }
}
