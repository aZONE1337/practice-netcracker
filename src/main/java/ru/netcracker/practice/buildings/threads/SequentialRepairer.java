package ru.netcracker.practice.buildings.threads;

import ru.netcracker.practice.buildings.interfaces.Floor;

public class SequentialRepairer implements Runnable {
    private final Floor floor;

    public SequentialRepairer(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {

    }
}
