package ru.netcracker.practice.buildings.net.client;

import ru.netcracker.practice.buildings.exceptions.BuildingUnderArrestException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class SerialClient {
    private static final int PORT = 1337;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String objsPath = "src/main/resources/buildings.txt";
        String typesPath = "src/main/resources/building_types.txt";

        Path buildings = Paths.get(objsPath);
        Path types = Paths.get(typesPath);

        try (Socket socket = new Socket("localhost", PORT);
             ObjectOutputStream out = new ObjectOutputStream(
                     socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(
                     socket.getInputStream());
             BufferedWriter fOut = new BufferedWriter(
                     new FileWriter(
                             new File("src/main/resources/buildings_prices.txt"))))
        {
            System.out.println("I/O channels opened");

            while (true) {
                System.out.println("Want to resume? Y/N");
                if (!scanner.nextLine().toLowerCase().contains("y"))
                    break;

                List<Building> forRequests = BinaryClient.prepareData(buildings, types);
                System.out.println("Data for requests prepared");

                for (Building b : forRequests) {
                    System.out.println("Want to continue? Y/N");
                    if (!scanner.nextLine().toLowerCase().contains("y"))
                        break;

                    Buildings.serializeBuilding(b, out);
                    System.out.println("Sent request to server");

                    Object response = in.readObject();
                    if (response instanceof BuildingUnderArrestException) {
                        response = ((BuildingUnderArrestException) response).getMessage();
                    }
                    System.out.println("Price from response: " + response.toString());

                    System.out.println("Writing to file...");
                    fOut.write(response.toString() + "\n");
                    fOut.flush();
                }
                System.out.println("Prices file is ready");
            }
            System.out.println("Closing resources...");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
