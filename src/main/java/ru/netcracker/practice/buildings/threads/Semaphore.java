package ru.netcracker.practice.buildings.threads;

public class Semaphore {
    private int permits;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        if (permits == 0)
            this.wait();
        else
            permits--;
    }


    public synchronized void release() throws InterruptedException {
        permits++;
        this.notify();
    }
}
