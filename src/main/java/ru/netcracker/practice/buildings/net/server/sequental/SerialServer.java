package ru.netcracker.practice.buildings.net.server.sequental;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(1337)) {

            while (true) {
                Socket client = server.accept();
                System.out.println("Client connected");

                ObjectInputStream in = new ObjectInputStream(
                        client.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(
                        client.getOutputStream());
                System.out.println("I/O channels opened");

                while (client.isConnected()) {

                    Object request = Buildings.deserializeBuilding(in);
                    System.out.println("Got new request");

                    System.out.println("Preparing response");
                    Object response;
                    try {
                        response = BinaryServer.costEstimate((Building) request) + "$";
                    } catch (BuildingUnderArrestException e) {
                        response = new BuildingUnderArrestException("");
                        e.printStackTrace();
                    }
                    out.writeObject(response);
                    out.flush();
                    System.out.println("Response sent");
                }
                System.out.println("Client disconnected");
                in.close();
                out.close();
                client.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
