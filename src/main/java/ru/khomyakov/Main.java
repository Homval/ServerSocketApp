package ru.khomyakov;



import java.io.IOException;

public class Main {
    public static void main( String[] args ) throws IOException {
        ThreadPoolServer server = new ThreadPoolServer();
        server.run();


    }
}
