package ru.ideaplatform.flights.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ideaplatform.flights.model.Flight;
import ru.ideaplatform.flights.model.Flights;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class FlightServiceImpl implements FlightService {
    private static final String DEFAULT = "flights.json";
    private final Gson gson;

    @Override
    public List<Flight> load(String path) throws Exception {
        JsonReader reader = new JsonReader(getReader(path));
        Flights asList = gson.fromJson(reader, Flights.class);
        return Arrays.asList(asList.getFlights());
    }

    private InputStreamReader getReader(String path) throws Exception {
        if (path.isEmpty()) {
            ClassLoader classLoader = getClass().getClassLoader();
            return new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(DEFAULT)));
        } else if (!path.endsWith(".json")) {
            throw new Exception("Wrong file format");
        } else {
            return new FileReader(path);
        }
    }
}
