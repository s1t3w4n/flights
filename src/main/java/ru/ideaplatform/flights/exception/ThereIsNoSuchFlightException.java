package ru.ideaplatform.flights.exception;

public class ThereIsNoSuchFlightException extends Exception {
    public ThereIsNoSuchFlightException() {
        super("Sorry! There is no such flight.");
    }
}
