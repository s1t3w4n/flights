package ru.ideaplatform.flights.utils;

import ru.ideaplatform.flights.model.Flight;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleUtils {

    private static final String NEW_LINE = "\n";

    public static String help() {
        return "Commands:" + NEW_LINE +
                "1. To see all commands use commands 'h' or 'help'" +
                NEW_LINE +
                "2. To load data from use commands 'l' or 'load'" +
                NEW_LINE +
                "Optional: You can use path to your custom file, as default will be used embedded data" +
                NEW_LINE +
                "3. To find the highest and the lowest price of flight use commands 'max' and 'min'" +
                NEW_LINE +
                "Optional: You can set your custom 'to' and 'from' directions, as default will be used 'Москва' and 'Хабаровск'" +
                NEW_LINE +
                "4. To find an average price of flights of the current direction use commands 'a','ave' or 'average'" +
                "Optional: You can set your custom 'to' and 'from' directions, as default will be used 'Москва' and 'Хабаровск'"
                ;
    }

    public static String listToString(List<Flight> list) {
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(";\n"));
    }

}
