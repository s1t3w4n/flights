package ru.ideaplatform.flights.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.ideaplatform.flights.exception.NotJsonFileFormat;
import ru.ideaplatform.flights.model.Flight;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testing flight service with data")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(FlightServiceImpl.class)
class FlightServiceImplWithDataTest {

    private static final String EMPTY_VALUE_PARAMETER = "";
    private static final Flight LOWEST_DEFAULT_FLIGHT = new Flight("Москва", "Хабаровск", 11000);
    private static final Flight HIGHEST_DEFAULT_FLIGHT = new Flight("Москва", "Хабаровск", 12500);
    public static final BigDecimal DEFAULT_DECIMAL = new BigDecimal("11833.33");

    @Autowired
    private FlightService service;

    @BeforeEach
    public void fillData() throws NotJsonFileFormat, FileNotFoundException {
        service.load(EMPTY_VALUE_PARAMETER);
    }

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