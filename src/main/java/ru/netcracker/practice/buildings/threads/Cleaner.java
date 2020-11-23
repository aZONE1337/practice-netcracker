package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public class Cleaner extends Thread {
    private final Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        synchronized (this.floor) {
            if (!isInterrupted()) {
                int index = 0;
                for (Space space : floor.getSpacesAsArray()) {
                    synchronized (Space.class) {
                        System.out.println("Cleaning room number " +
                                index +
                                " with total area " +
                                space.getArea() +
                                " square meters");
                        index++;
                    }
                    if (index == floor.getTotalSpaces()) {
                        System.out.println("0 spaces left! Ended cleaning.");
                    }
                }
            } else {
                System.out.println("Cleaner interrupted! Ended cleaning.");
            }
        }
    }
}
