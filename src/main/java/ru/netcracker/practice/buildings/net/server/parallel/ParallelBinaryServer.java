package ru.netcracker.practice.buildings.net.server.parallel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.netcracker.practice.buildings.net.server.sequential.BinaryServer;

public class ParallelBinaryServer {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final int PORT = 1338;
    private static int count = 0;


    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

        System.out.println("Server started");

        while(true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                executorService.execute(new ServerThread(client, ++count));
                System.out.println("Started thread #" + count);

                System.out.println("Waiting for another client...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServerThread implements Runnable {
        private final Socket client;
        private final int number;

        public ServerThread(Socket client, int number) {
            this.client = client;
            this.number = number;
        }

        @Override
        public void run() {
            BinaryServer.respond(client, number);
        }
    }
}
