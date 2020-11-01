package ru.ideaplatform.flights.exception;

public class NotJsonFileFormat extends Exception {
    public NotJsonFileFormat() {
        super("Wrong file format. File, must be .json");
    }
}
