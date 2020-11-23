package ru.netcracker.practice.buildings.net.server.parallel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import ru.netcracker.practice.buildings.interfaces.Building;

public class BinaryServer {
    private static final int PORT = 1337;
    private static int counter = 1;


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server started");

        while (true) {
            try {
                Socket socket = server.accept();

                new Thread(() -> {
                    System.out.println("Connection started in a new thread #" + ++counter);
                    try (BufferedReader in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                         PrintWriter out = new PrintWriter(
                                 new BufferedWriter(
                                         new OutputStreamWriter(socket.getOutputStream())), true))
                    {
                        System.out.println("I/O ready");
                        while (socket.isConnected()) {
                            String request = in.readLine();
                            System.out.println("Received new request");

                            System.out.println("Preparing response...");
                            String response = createResponse(request);

                            out.println(response);
                            System.out.println("Response sent");
                        }
                    } catch (IOException e) {
                        System.out.println("Closing connection...");
                        e.printStackTrace();
                    }
                }).start();

                System.out.println("Waiting for another client...");
            } finally {
                server.close();
            }
        }
    }

    private static String createResponse(String request) {
        List<Building> buildings = ru.netcracker.practice.buildings.net.server.sequental.BinaryServer.getFromLine(request);
        return ru.netcracker.practice.buildings.net.server.sequental.BinaryServer.createResponse(buildings);
    }
}
