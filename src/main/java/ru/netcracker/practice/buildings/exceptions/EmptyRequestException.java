package ru.netcracker.practice.buildings.exceptions;

public class EmptyRequestException extends RuntimeException {

    public EmptyRequestException(String string) {
        super(string);
    }
}
