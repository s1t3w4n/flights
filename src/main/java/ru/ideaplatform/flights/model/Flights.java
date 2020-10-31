package ru.ideaplatform.flights.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Flights {
    private final Flight[] flights;
}
