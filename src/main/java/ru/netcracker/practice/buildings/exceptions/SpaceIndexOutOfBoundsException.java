package ru.netcracker.practice.buildings.exceptions;

public class SpaceIndexOutOfBoundsException extends IndexOutOfBoundsException {

    public SpaceIndexOutOfBoundsException(String s) {
        super(s);
    }

    public SpaceIndexOutOfBoundsException(int index) {
        super("Index: " + index);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
