package ru.netology;

import ru.netology.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import static ru.netology.utils.PropertyLoader.*;

// https://github.com/netology-code/jd-homeworks/blob/master/diploma/networkchat.md
public class Server {

    static LinkedList<ClientHandler> clients = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(getProperty("server.port"));
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started. Waiting connections on port: " + port);
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, Logger.getInstance());
                clients.add(clientHandler);
            }
        } finally {
            serverSocket.close();
        }
    }
}
