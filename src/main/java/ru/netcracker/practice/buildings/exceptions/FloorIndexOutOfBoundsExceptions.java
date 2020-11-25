package ru.netcracker.practice.buildings.exceptions;

public class FloorIndexOutOfBoundsExceptions extends IndexOutOfBoundsException {
    public FloorIndexOutOfBoundsExceptions(String s) {
        super(s);
    }

    public FloorIndexOutOfBoundsExceptions(int index) {
        super("Index: " + index);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
