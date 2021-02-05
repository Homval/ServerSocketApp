package ru.khomyakov;

public class Main {
    public static void main( String[] args ) throws InterruptedException {
        ThreadPoolServer server = new ThreadPoolServer();
        Thread workerThread = new Thread(server);
        workerThread.start();

        System.out.println("Server started");

        workerThread.join();
    }
}
