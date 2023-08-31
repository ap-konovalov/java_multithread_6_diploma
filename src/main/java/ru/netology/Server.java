package ru.netology;

import ru.netology.utils.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.netology.utils.PropertyLoader.*;

// https://github.com/netology-code/jd-homeworks/blob/master/diploma/networkchat.md
public class Server {

    static LinkedList<ClientHandler> clients = new LinkedList<>();

    static ExecutorService executeIt = Executors.newFixedThreadPool(Integer.parseInt(getProperty("server.max_clients")));

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(getProperty("server.port")))) {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, Logger.getInstance());
                clients.add(clientHandler);
                executeIt.execute(clientHandler);
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
