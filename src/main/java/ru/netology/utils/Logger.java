package ru.netology.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    private static Logger INSTANCE = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (INSTANCE == null) {
            synchronized (Logger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Logger();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void log(String clientName, String message) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/log.txt", true))
        ) {
            bw.write("[" + LocalDateTime.now() + "] " + clientName + ": " + message);
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
