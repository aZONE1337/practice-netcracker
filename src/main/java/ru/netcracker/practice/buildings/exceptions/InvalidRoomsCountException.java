package ru.netcracker.practice.buildings.exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
    public InvalidRoomsCountException(int rooms) {
        super("Rooms: " + rooms);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
