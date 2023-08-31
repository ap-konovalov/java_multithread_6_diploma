package ru.netology;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        int j = 0;
        while (j < 5) {
            j++;
            exec.execute(new TestRunnableClientTester(j));
        }
        exec.shutdown();
    }
}
