package ru.ideaplatform.flights.service;

import ru.ideaplatform.flights.exception.NoDataException;
import ru.ideaplatform.flights.exception.ThereIsNoSuchFlightException;
import ru.ideaplatform.flights.model.Flight;

import java.util.List;

public interface FlightService {
    List<Flight> load(String path) throws Exception;

    Flight findLowestPrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException;

    Flight findHighestPrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException;

    double findAveragePrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException;
}
