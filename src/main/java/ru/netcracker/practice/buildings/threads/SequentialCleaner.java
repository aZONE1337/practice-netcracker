package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;

public class SequentialCleaner implements Runnable {
    private final Floor floor;

    public SequentialCleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {

    }
}
