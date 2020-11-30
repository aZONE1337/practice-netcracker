package ru.netcracker.practice.buildings.util.other;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.factory.BuildingFactory;
import ru.netcracker.practice.buildings.util.factory.DwellingFactory;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class Buildings {

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void outputBuilding(Building building, OutputStream out) throws IOException {
        BufferedOutputStream bufOut = new BufferedOutputStream(out);
        bufOut.write((deconstruct(building) + "\n").getBytes());
        bufOut.flush();
    }

    public static Building inputBuilding(InputStream in) throws IOException {
        BufferedInputStream bufIn = new BufferedInputStream(in);
        int x;
        StringBuilder sb = new StringBuilder();
        while ((x = bufIn.read()) != 10) {
            sb.append((char) x);
        }
        return getBuildingFromString(sb.toString());
    }

    public static Building inputBuilding(InputStream in,
                                         Class<? extends Building> buildingClass,
                                         Class<? extends Floor> floorClass,
                                         Class<? extends Space> spaceClass) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BufferedInputStream bufIn = new BufferedInputStream(in);
        int x;
        StringBuilder sb = new StringBuilder();
        while ((x = bufIn.read()) != 10) {
            sb.append((char) x);
        }
        return getBuildingFromString(sb.toString(), buildingClass, floorClass, spaceClass);
    }

    public static void writeBuilding(Building building, Writer out) throws IOException {
        BufferedWriter bufOut = new BufferedWriter(out);
        bufOut.write(deconstruct(building));
        bufOut.newLine();
        bufOut.flush();
    }

    public static Building readBuilding(Reader in) throws IOException {
        BufferedReader bufIn = new BufferedReader(in);
        String line = bufIn.readLine();
        return getBuildingFromString(line);
    }

    public static void serializeBuilding(Building building, OutputStream out) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(building);
        objOut.flush();
    }

    public static Object deserializeBuilding(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream objIn = new ObjectInputStream(in);
        return objIn.readObject();
    }

    public static Building getBuildingFromString(String building) {
        String[] params = building.split(" ");
        Floor[] floors = new Floor[Integer.parseInt(params[0])];
        int k = 1;
        for (int i = 0; i < Integer.parseInt(params[0]); i++) {
            int spacesAmount = Integer.parseInt(params[k]);
            Space[] spaces = new Space[spacesAmount];
            for (int j = 0; j < spacesAmount; j++) {
                Space space = createSpace(Integer.parseInt(params[j * 2 + k + 1]), Float.parseFloat(params[j * 2 + k + 2]));
                spaces[j] = space;
            }
            floors[i] = createFloor(spaces);
            k += 2 * spacesAmount + 1;
        }
        return createBuilding(floors);
    }

    private static Building getBuildingFromString(String buildingStr,
                                                  Class<? extends Building> buildingClass,
                                                  Class<? extends Floor> floorClass,
                                                  Class<? extends Space> spaceClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String[] params = buildingStr.split(" ");
        Floor[] floors = new Floor[Integer.parseInt(params[0])];
        int k = 1;
        for (int i = 0; i < Integer.parseInt(params[0]); i++) {
            int spacesAmount = Integer.parseInt(params[k]);
            Space[] spaces = new Space[spacesAmount];
            for (int j = 0; j < spacesAmount; j++) {
                Space space = createSpace(spaceClass, Integer.parseInt(params[j * 2 + k + 1]), Float.parseFloat(params[j * 2 + k + 2]));
                spaces[j] = space;
            }
            floors[i] = createFloor(floorClass, spaces);
            k += 2 * spacesAmount + 1;
        }
        return createBuilding(buildingClass, floors);
    }

    private static String deconstruct(Building building) {
        StringBuilder sb = new StringBuilder();
        sb.append(building.getBuildingFloors()).append(" ");
        Floor[] floors = building.getFloorsAsArray();
        for (Floor floor : floors) {
            sb.append(floor.getTotalSpaces()).append(" ");
            Space[] spaces = floor.getSpacesAsArray();
            for (Space space : spaces) {
                sb.append(space.getRooms()).append(" ");
                sb.append(space.getArea()).append(" ");
            }
        }
        return sb.substring(0, sb.toString().length() - 1);
    }

    public static <T> void sort(T[] objects, Comparator<? super T> comparator) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < objects.length - 1; i++) {
                if (comparator.compare(objects[i], objects[i + 1]) < 0) {
                    isSorted = false;
                    T temp = objects[i];
                    objects[i] = objects[i + 1];
                    objects[i + 1] = temp;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] objects) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < objects.length - 1; i++) {
                if (objects[i].compareTo(objects[i + 1]) < 0) {
                    isSorted = false;
                    T temp = objects[i];
                    objects[i] = objects[i + 1];
                    objects[i + 1] = temp;
                }
            }
        }
    }

    public static Space createSpace(float area) {
        return buildingFactory.createSpace(area);
    }

    public static Space createSpace(int rooms, float area) {
        return buildingFactory.createSpace(rooms, area);
    }

    public static Space createSpace(Class<? extends Space> spaceClass, int rooms, float area) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return spaceClass.getDeclaredConstructor(int.class, float.class).newInstance(rooms, area);
    }

    public static Floor createFloor(int spacesAmount) {
        return buildingFactory.createFloor(spacesAmount);
    }

    public static Floor createFloor(Space... spaces) {
        return buildingFactory.CreateFloor(spaces);
    }

    public static Floor createFloor(Class<? extends Floor> floorClass, Space... spaces) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return floorClass.getDeclaredConstructor(Space[].class).newInstance((Object) spaces);
    }

    public static Building createBuilding(int floorsAmount, int[] spacesOnFloor) {
        return buildingFactory.createBuilding(floorsAmount, spacesOnFloor);
    }

    public static Building createBuilding(Floor... floors) {
        return buildingFactory.createBuilding(floors);
    }

    public static Building createBuilding(Class<? extends Building> buildingClass, Floor... floors) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return buildingClass.getDeclaredConstructor(Floor[].class).newInstance((Object) floors);
    }

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public static SynchronizedFloor synchronizedFloor(Floor floor) {
        return new SynchronizedFloor(floor);
    }
}
