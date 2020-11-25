package ru.netcracker.practice.buildings.exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException {
    public InvalidSpaceAreaException(float area) {
        super("Area: " + area);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
