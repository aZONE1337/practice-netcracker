package ru.netcracker.practice.buildings.net.server.parallel;

import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.net.server.sequental.BinaryServer;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialServer {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final int PORT = 1337;
    private static int count = 0;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started");
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("Client connected");

                executorService.execute(new ServerThread(client, ++count));
                System.out.println("Started thread #" + count);

                System.out.println("Waiting for new client...");
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
            try {
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                System.out.println("I/O ready");

                while (client.isConnected()) {
                    Building request;
                    try {
                        request = (Building) Buildings.deserializeBuilding(in);
                    } catch (EOFException e) {
                        break;
                    }
                    System.out.println("Received new request");

                    System.out.println("Preparing respond...");
                    String respond;
                    try {
                        respond = BinaryServer.costEstimate(request) + "$";
                    } catch (BuildingUnderArrestException e) {
                        respond = "Building under arrest";
                    }

                    out.println(respond);
                    System.out.println("Respond sent");
                }
                System.out.println("Client #" + number + " disconnected");
                System.out.println("Closing resources...");
                in.close();
                out.close();
                client.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
