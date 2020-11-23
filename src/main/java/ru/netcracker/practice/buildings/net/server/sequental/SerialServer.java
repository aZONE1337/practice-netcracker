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

            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("Client connected");

                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                System.out.println("I/O channels opened");

                while (client.isConnected()) {

                    Building request = (Building) Buildings.deserializeBuilding(in);
                    System.out.println("Received request");
                    System.out.println(request.toString());

                    System.out.println("Preparing respond");
                    String respond;
                    try {
                        respond = BinaryServer.costEstimate(request) + "$";
                    } catch (BuildingUnderArrestException e) {
                        respond = "Building is under arrest";
                        e.printStackTrace();
                    }
                    out.write(respond);
                    out.newLine();

                    out.flush();
                    System.out.println("Respond sent");
                }

                System.out.println("Closing resources...");
                in.close();
                out.close();
                System.out.println("I/O channels closed");
                client.close();
                System.out.println("Client disconnected");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
