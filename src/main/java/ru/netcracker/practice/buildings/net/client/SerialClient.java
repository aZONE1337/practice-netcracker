package ru.netcracker.practice.buildings.net.client;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.net.server.sequental.BinaryServer;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class SerialClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 1337);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()))
//             BufferedOutputStream fOut = new BufferedOutputStream(
//                     new FileOutputStream(
//                             new File("src/main/resources/buildings_prices.txt"))))
        {
            System.out.println("I/O channels opened");

            while (!socket.isOutputShutdown()) {
                System.out.println("Want to resume? Y/N");
                if (!scanner.nextLine().toLowerCase().contains("y"))
                    break;

                List<Building> buildings = prepareData();
                System.out.println("Data for requests prepared");

                for (Building b : buildings) {
                    System.out.println("Want to continue? Y/N");
                    if (!scanner.nextLine().toLowerCase().contains("y"))
                    break;

                    Buildings.serializeBuilding(b, out);
                    System.out.println("Sent request to server");
                    System.out.println("Type: " + b.getClass().getSimpleName() + ", total space: " + b.getBuildingArea());

                    System.out.println("Waiting for server respond");

                    String respond = in.readLine();
                    System.out.println("Price: " + respond);

//                    System.out.println("Writing to file...");
//                    fOut.write((respond + "\n").getBytes());
//                    fOut.flush();
                }
                System.out.println("Prices file is ready");
                break;
            }
            System.out.println("Closing resources...");
            System.out.println("Connection closed");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Building> prepareData() throws IOException {
        Path toBuildings = Paths.get("src/main/resources/buildings.txt");
        Path toTypes = Paths.get("src/main/resources/building_types.txt");

        return BinaryServer.getFromLine(
                BinaryClient.readBuildings(toBuildings, toTypes));
    }
}
