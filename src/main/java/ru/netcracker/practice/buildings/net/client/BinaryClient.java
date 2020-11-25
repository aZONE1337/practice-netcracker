package ru.netcracker.practice.buildings.net.client;

import ru.netcracker.practice.buildings.exceptions.InputFilesDifferentLengthException;
import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.util.factory.DwellingFactory;
import ru.netcracker.practice.buildings.util.factory.HotelFactory;
import ru.netcracker.practice.buildings.util.factory.OfficeFactory;
import ru.netcracker.practice.buildings.util.other.Buildings;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryClient {
    private static final int PORT = 1338;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String objsPath = "src/main/resources/buildings.txt";
        String typesPath = "src/main/resources/building_types.txt";

        Path buildings = Paths.get(objsPath);
        Path types = Paths.get(typesPath);

        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(
                     new OutputStreamWriter(
                             socket.getOutputStream()));
             BufferedWriter fOut = new BufferedWriter(
                     new FileWriter(
                             new File("src/main/resources/buildings_prices.txt"))))
        {
            System.out.println("I/O channels opened");

            while (true) {
                System.out.println("Want to resume? Y/N");
                if (!scanner.nextLine().toLowerCase().contains("y"))
                    break;

                List<Building> forRequests = prepareData(buildings, types);
                System.out.println("Data for requests prepared");

                for (Building request : forRequests) {
                    System.out.println("Want to continue? Y/N");
                    if (!scanner.nextLine().toLowerCase().contains("y"))
                        break;

                    Buildings.writeBuilding(request, out);
                    System.out.println("Sent request to server");

                    String response = in.readLine();
                    System.out.println("Price from response: " + response);

                    System.out.println("Writing to file...");
                    fOut.write(response + "\n");
                    fOut.flush();
                }
                System.out.println("Prices file is ready");
            }
            System.out.println("Closing resources...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Building> prepareData(Path buildings, Path types) throws IOException {
        List<String> bLines = Files.readAllLines(buildings);
        List<String> tLines = Files.readAllLines(types);
        List<Building> result = new ArrayList<>();

        if (bLines.size() != tLines.size()) {
            throw new InputFilesDifferentLengthException(
                    "buildings file size: " + bLines.size() + " lines" +
                    ", types file size: " + tLines.size() + " lines"
            );
        }

        for (int i = 0; i < bLines.size(); i++) {
            if (tLines.get(i).equals("Dwelling"))
                Buildings.setBuildingFactory(new DwellingFactory());
            if (tLines.get(i).equals("Hotel"))
                Buildings.setBuildingFactory(new HotelFactory());
            if (tLines.get(i).equals("Office"))
                Buildings.setBuildingFactory(new OfficeFactory());

            result.add(Buildings.getBuildingFromString(bLines.get(i)));
        }

        return result;
    }
}
