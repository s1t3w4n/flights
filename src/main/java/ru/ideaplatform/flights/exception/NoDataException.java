package ru.ideaplatform.flights.exception;

public class NoDataException extends Exception {
    public NoDataException() {
        super("There is no flights. Load some data.");
    }
}
