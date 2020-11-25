package ru.netcracker.practice.buildings.net.client;

import ru.netcracker.practice.buildings.exceptions.InputFilesDifferentLengthException;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String objsPath = "src/main/resources/buildings.txt";
        String typesPath = "src/main/resources/building_types.txt";

        Path buildings = Paths.get(objsPath);
        Path types = Paths.get(typesPath);

        try (Socket socket = new Socket("localhost", 3345)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()));
            BufferedWriter fOut = new BufferedWriter(
                    new FileWriter(
                            new File("src/main/resources/buildings_prices.txt")));
            System.out.println("I/O channels opened");

            while (true) {
                System.out.println("Want to resume? Y/N");
                if (!scanner.nextLine().toLowerCase().contains("y"))
                    break;

                List<String> forRequests = prepareDataLines(buildings, types);
                System.out.println("Data for requests prepared");

                for (String request : forRequests) {
                    System.out.println("Want to continue? Y/N");
                    if (!scanner.nextLine().toLowerCase().contains("y"))
                        break;

                    out.write(request);
                    out.newLine();
                    out.flush();
                    System.out.println("Sent request to server");

                    String response = in.readLine();
                    System.out.println("Price from response: " + response);

                    System.out.println("Writing to file...");
                    fOut.write(response);
                    fOut.flush();
                }
                System.out.println("Prices file is ready");
            }
            System.out.println("Closing resources...");
            in.close();
            out.close();
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> prepareDataLines(Path buildings, Path types) throws IOException {
        List<String> bLines = Files.readAllLines(buildings);
        List<String> tLines = Files.readAllLines(types);
        List<String> result = new ArrayList<>();

        if (bLines.size() != tLines.size()) {
            throw new InputFilesDifferentLengthException(
                    "buildings file size: " + bLines.size() + " lines" +
                    ", types file size: " + tLines.size() + " lines"
            );
        }

        for (int i = 0; i < bLines.size(); i++) {
            result.add(bLines.get(i) + "_" + tLines.get(i));
        }

        return result;
    }
}
