package ru.khomyakov;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;



public class ThreadPoolServer implements Runnable {
    private ServerSocket serverSocket;
    InetAddress inetAddress = InetAddress.getByName(null);

    public ThreadPoolServer() throws IOException {
        serverSocket = new ServerSocket(5050, 10, inetAddress);
        System.out.println("Server started");
//        run();
    }

    @Override
    public void run() {
        while (true) {
            try (Socket clientSocket = serverSocket.accept()){
                new ServerWorker(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ServerWorker implements Runnable{
        Socket socket;

        public ServerWorker(Socket socket) {
            this.socket = socket;
            run();
        }

        @Override
        public void run() {
            try{
                BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                String line;
                while (!(line = is.readLine()).equals("Bye")) {
                    System.out.println(line);
                    out.println(line);
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
