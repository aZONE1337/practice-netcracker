package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;

import java.util.concurrent.Semaphore;

public class SequentialCleaner implements Runnable {
    private final Floor floor;
    private final Semaphore semaphore;

    public SequentialCleaner(Floor floor, Semaphore semaphore) {
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        synchronized (this.floor) {
            try {
                int index = 0;
                for (Space space : floor.getSpacesAsArray()) {
                    semaphore.acquire();
                    System.out.println("Cleaning room number " +
                            index +
                            " with total area " +
                            space.getArea() +
                            " square meters");
                    index++;
                    if (index == floor.getTotalSpaces()) {
                        System.out.println("0 spaces left! Ended cleaning.");
                    }
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
