package ru.ideaplatform.flights.service;

import ru.ideaplatform.flights.model.Flight;

import java.io.FileNotFoundException;
import java.util.List;

public interface FlightService {
    List<Flight> load(String path) throws Exception;
}
