package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

public class Repairer extends Thread {
    private final Floor floor;

    public Repairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        synchronized (this.floor) {
            if (!isInterrupted()) {
                int index = 0;
                for (Space space : floor.getSpacesAsArray()) {
                    System.out.println("Repairing space number " +
                            index +
                            " with total area " +
                            space.getArea() +
                            " square meters");
                    index++;
                    if (index == floor.getTotalSpaces()) {
                        System.out.println("0 spaces left! Ended repairing.");
                    }
                }
            } else {
                System.out.println("Repairer interrupted! Ended repairing.");
            }
        }
    }
}
