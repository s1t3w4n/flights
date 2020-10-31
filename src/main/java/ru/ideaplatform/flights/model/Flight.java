package ru.ideaplatform.flights.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Flight {
    private final String fromCity;
    private final String toCity;
    private final int price;
}
