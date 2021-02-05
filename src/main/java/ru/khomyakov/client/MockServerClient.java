package ru.khomyakov.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MockServerClient {
    private Socket socket;

    public MockServerClient() throws IOException {
        InetAddress inetAddress = InetAddress.getByName(null);
        System.out.println(inetAddress);
        socket = new Socket(inetAddress, 5050);
    }


    public static void main(String[] args) throws IOException {
        MockServerClient mockServerClient = new MockServerClient();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(mockServerClient.socket.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mockServerClient.socket.getOutputStream())), true);
            for (int count = 0; count < 10; count++) {
                out.println(count);
                String resp = in.readLine();
                System.out.println(resp + " - " + count);
            }
            out.println("Bye");
            System.out.println("Socket closing");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
