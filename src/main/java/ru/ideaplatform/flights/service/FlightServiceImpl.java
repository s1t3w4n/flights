package ru.ideaplatform.flights.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ideaplatform.flights.exception.NoDataException;
import ru.ideaplatform.flights.exception.NotJsonFileFormat;
import ru.ideaplatform.flights.exception.ThereIsNoSuchFlightException;
import ru.ideaplatform.flights.model.Flight;
import ru.ideaplatform.flights.model.Flights;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {
    private static final String DEFAULT_FILE = "flights.json";
    private static final String DEFAULT_FROM = "Москва";
    private static final String DEFAULT_TO = "Хабаровск";

    private final Gson gson;
    private Map<String, Map<String, LinkedList<Flight>>> data;

    @Override
    public List<Flight> load(String path) throws NotJsonFileFormat, IOException {
        JsonReader reader = new JsonReader(getReader(path));
        Flights asList = gson.fromJson(reader, Flights.class);
        data = fillData(asList);
        return Arrays.asList(asList.getFlights());
    }

    //TODO refactor duplicated code
    @Override
    public Flight findLowestPrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException {
        if (data.isEmpty()) {
            throw new NoDataException();
        } else if (isDefaultFlightExist() && from.isEmpty() || to.isEmpty()) {
            return data.get(DEFAULT_FROM).get(DEFAULT_TO).getFirst();
        } else if (isFlightExist(from, to)) {
            return data.get(from).get(to).getFirst();
        } else {
            throw new ThereIsNoSuchFlightException();
        }
    }

    //TODO refactor duplicated code
    @Override
    public Flight findHighestPrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException {
        if (data.isEmpty()) {
            throw new NoDataException();
        } else if (isDefaultFlightExist() && from.isEmpty() || to.isEmpty()) {
            return data.get(DEFAULT_FROM).get(DEFAULT_TO).getLast();
        } else if (isFlightExist(from, to)) {
            return data.get(from).get(to).getLast();
        } else {
            throw new ThereIsNoSuchFlightException();
        }
    }

    //TODO refactor duplicated code
    @Override
    public double findAveragePrice(String from, String to) throws ThereIsNoSuchFlightException, NoDataException {
        if (data.isEmpty()) {
            throw new NoDataException();
        } else if (isDefaultFlightExist() && from.isEmpty() || to.isEmpty()) {
            return calcAveragePrice(data.get(DEFAULT_FROM).get(DEFAULT_TO));
        } else if (isFlightExist(from, to)) {
            return calcAveragePrice(data.get(from).get(to));
        } else {
            throw new ThereIsNoSuchFlightException();
        }
    }

    private InputStreamReader getReader(String path) throws NotJsonFileFormat, IOException {
        if (path.isEmpty()) {
            ClassLoader classLoader = getClass().getClassLoader();
            return new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(DEFAULT_FILE)), StandardCharsets.UTF_8);
        } else if (!path.endsWith(".json")) {
            throw new NotJsonFileFormat();
        } else {
            return new FileReader(path, StandardCharsets.UTF_8);
        }
    }

    private Map<String, Map<String, LinkedList<Flight>>> fillData(Flights flights) {
        Map<String, Map<String, LinkedList<Flight>>> map = new HashMap<>();
        for (Flight flight : flights.getFlights()) {
            if (map.containsKey(flight.getFromCity())) {
                if (map.get(flight.getFromCity()).containsKey((flight.getToCity()))) {
                    map.get(flight.getFromCity()).get(flight.getToCity()).add(flight);
                } else {
                    fillMap(map, flight);
                }
            } else {
                map.put(flight.getFromCity(), new HashMap<>());
                fillMap(map, flight);
            }
        }
        sort(map);
        return map;
    }

    private void fillMap(Map<String, Map<String, LinkedList<Flight>>> map, Flight flight) {
        map.get(flight.getFromCity()).put(flight.getToCity(), new LinkedList<>());
        map.get(flight.getFromCity()).get(flight.getToCity()).add(flight);
    }

    private void sort(Map<String, Map<String, LinkedList<Flight>>> map) {
        for (Map<String, LinkedList<Flight>> value : map.values()) {
            for (List<Flight> flights : value.values()) {
                flights.sort(Comparator.comparingInt(Flight::getPrice));
            }
        }
    }

    private boolean isFlightExist(String from, String to) {
        return data.containsKey(from) && data.get(from).containsKey(to);
    }

    private boolean isDefaultFlightExist() {
        return isFlightExist(DEFAULT_FROM, DEFAULT_TO);
    }

    private double calcAveragePrice(List<Flight> flights) {
        return Arrays.stream(flights.stream().map(Flight::getPrice).collect(Collectors.toList())
                .stream().mapToInt(Integer::intValue).toArray())
                .average().orElse(Double.NaN);
    }

}
