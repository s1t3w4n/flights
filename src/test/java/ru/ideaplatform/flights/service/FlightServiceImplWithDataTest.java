package ru.ideaplatform.flights.service;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.ideaplatform.flights.model.Flight;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing flight service with data")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class FlightServiceImplWithDataTest {

    private static final String EMPTY_VALUE_PARAMETER = "";
    private static final Flight LOWEST_DEFAULT_FLIGHT = new Flight("Москва", "Хабаровск", 11000);
    private static final Flight HIGHEST_DEFAULT_FLIGHT = new Flight("Москва", "Хабаровск", 12500);
    private static final Flight EXTRA_FLIGHT = new Flight("Москва", "Хабаровск", 12000);
    private static final BigDecimal DEFAULT_DECIMAL = new BigDecimal("11833.33");
    private static final Map<String, Map<String, LinkedList<Flight>>> data = new HashMap<>();
    static {
        data.put("Москва", new HashMap<>());
        data.get("Москва").put("Хабаровск", new LinkedList<>());
        data.get("Москва").get("Хабаровск").add(LOWEST_DEFAULT_FLIGHT);
        data.get("Москва").get("Хабаровск").add(EXTRA_FLIGHT);
        data.get("Москва").get("Хабаровск").add(HIGHEST_DEFAULT_FLIGHT);
    }

    private final FlightService service = new FlightServiceImpl(new Gson(), data);

    @SneakyThrows
    @DisplayName("Should find lowest price with empty parameters")
    @Test
    void shouldFindLowestPrice() {
        assertThat(service.findLowestPrice(EMPTY_VALUE_PARAMETER, EMPTY_VALUE_PARAMETER))
                .isEqualTo(LOWEST_DEFAULT_FLIGHT);
    }

    @SneakyThrows
    @DisplayName("Should find highest price with empty parameters")
    @Test
    void shouldFindHighestPrice() {
        assertThat(service.findHighestPrice(EMPTY_VALUE_PARAMETER, EMPTY_VALUE_PARAMETER))
                .isEqualTo(HIGHEST_DEFAULT_FLIGHT);
    }

    @SneakyThrows
    @DisplayName("Should calc average price with empty parameters")
    @Test
    void shouldCalcAveragePrice() {
        assertThat(new BigDecimal(
                String.format(Locale.ROOT, "%.2f", service.findAveragePrice(EMPTY_VALUE_PARAMETER, EMPTY_VALUE_PARAMETER))))
                .isEqualTo(DEFAULT_DECIMAL);
    }
}