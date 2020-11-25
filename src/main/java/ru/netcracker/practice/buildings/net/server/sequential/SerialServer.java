package ru.netcracker.practice.buildings.net.server.sequential;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    private static final int PORT = 1337;
    private static int count = 0;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Server started");

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                respond(client, ++count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void respond(Socket client, int number) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                client.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(
                client.getInputStream()))
        {
            System.out.println("I/O channels opened");

            while (true) {
                Object request;
                try {
                    request = Buildings.deserializeBuilding(in);
                } catch (EOFException e) {
                    break;
                }
                System.out.println("Received new request");

                Object response;
                try {
                    response = BinaryServer.costEstimate((Building) request) + "$";
                } catch (BuildingUnderArrestException e) {
                    response = new BuildingUnderArrestException("Building under arrest");
                }
                out.writeObject(response);
                out.flush();
                System.out.println("Response sent");
            }
            System.out.println("Client #" + number + " disconnected");
            client.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
