package ru.netcracker.practice.buildings.net.client;

import ru.netcracker.practice.buildings.exceptions.InputFilesDifferentLengthException;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class BinaryClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 3345)) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            socket.getOutputStream()));
//            BufferedWriter fOut = new BufferedWriter(
//                    new FileWriter(
//                            new File("src/main/resources/buildings_prices.txt")));
            System.out.println("I/O created");

            while (true) {
                String request = null;

                String objsPath = "src/main/resources/buildings.txt";
                String typesPath = "src/main/resources/building_types.txt";
//                System.out.println("Enter path to buildings.txt");
//                objsPath = scanner.nextLine();
//                System.out.println("Enter path to building_types.txt");
//                typesPath = scanner.nextLine();
//
//                if (objsPath.equalsIgnoreCase("quit") ||
//                typesPath.equalsIgnoreCase("quit")) {
//                    request = "quit";
//                }

                System.out.println("Write \"quit\" to disconnect");
                if (scanner.nextLine().equalsIgnoreCase("quit")) {
                    request = "quit";
                }


                Path buildings = Paths.get(objsPath);
                Path types = Paths.get(typesPath);

                if (Files.exists(buildings) && Files.exists(types)) {
                    System.out.println("Preparing request...");
                    request = readBuildings(
                            buildings,
                            types
                    );
                }

                if (request != null) {
                    if (request.equalsIgnoreCase("quit")) {
                        out.write(request);
                        out.newLine();
                        out.flush();
                        socket.close();
                        System.out.println("Quit request");
                        System.out.println("Closing connection");
                        break;
                    }

                    out.write(request);
                    out.newLine();
                    System.out.println("Request ready");
                } else {
                    System.out.println("Request null");
                }

                out.flush();
                System.out.println("Request sent");

                String response = in.readLine();
                System.out.println("Price is: " + response);

//                System.out.println("Writing to file...");
//                fOut.write(response.replaceAll("_", "\n"));
//                fOut.flush();

                break;
            }
            in.close();
            out.close();
            System.out.println("I/O closed");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readBuildings(Path buildings, Path types) throws IOException {
        List<String> bLines = Files.readAllLines(buildings);
        List<String> tLines = Files.readAllLines(types);

        if (bLines.size() != tLines.size()) {
            throw new InputFilesDifferentLengthException(
                    "sizes: " + bLines.size() +
                    ", " + tLines.size()
            );
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bLines.size(); i++) {
            sb.append(bLines.get(i))
                    .append("_")
                    .append(tLines.get(i))
                    .append("-");
        }

        return sb.toString();
    }
}
