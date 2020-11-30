package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.util.concurrent.Semaphore;

public class SequentialRepairer implements Runnable {
    private final Floor floor;
    private final Semaphore semaphore;

    public SequentialRepairer(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            int index = 0;
            for (Space space : floor.getSpacesAsArray()) {
                semaphore.acquire();
                System.out.println("Repairing space number " +
                        index +
                        " with total area " +
                        space.getArea() +
                        " square meters");
                index++;
                if (index == floor.getTotalSpaces()) {
                    System.out.println("0 spaces left! Ended repairing.");
                }
                semaphore.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
