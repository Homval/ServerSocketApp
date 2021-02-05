package ru.khomyakov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ThreadPoolServer implements Runnable {
    private ServerSocket serverSocket = null;
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static boolean isStopped = false;


    @Override
    public void run() {
        openServerSocket();
        while (!isStopped) {
            try (Socket clientSocket = serverSocket.accept()){
                executorService.execute(new ServerWorker(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    private void openServerSocket() {
        try {
            int serverPort = 5050;
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerWorker implements Runnable{
        Socket socket;

        public ServerWorker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try{
                BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String line = is.readLine();
                if (line.equals("Bye")) {
                    isStopped = true;
                    return;
                }
                out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
