package ru.ideaplatform.flights.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.ideaplatform.flights.exception.NotJsonFileFormat;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Testing flight service for load")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@Import(FlightServiceImpl.class)
class FlightServiceImplTest {

    private static final String EMPTY_PATH_PARAMETER = "";
    private static final String WRONG_FILE_FORMAT_PARAMETER = "src/test/java/resources/wrongFileFormat.doc";
    private static final String WRONG_PATH_PARAMETER = "src/test/java/resources/asd.json";

    @Autowired
    private FlightService service;

    @DisplayName("Should return default data with empty path")
    @Test
    void shouldReturnDefaultDataWithEmptyPath() throws NotJsonFileFormat, FileNotFoundException {
        assertThat(service.load(EMPTY_PATH_PARAMETER)).hasSize(6);
    }

    @DisplayName("Should throw NotJsonFileFormat")
    @Test
    void shouldThrowWrongFileFormatParameter() {
        assertThatThrownBy(() -> service.load(WRONG_FILE_FORMAT_PARAMETER)).isInstanceOf(NotJsonFileFormat.class);
    }

    @DisplayName("Should throw FileNotFoundException")
    @Test
    void shouldThrowFileNotFound() {
        assertThatThrownBy(() -> service.load(WRONG_PATH_PARAMETER)).isInstanceOf(FileNotFoundException.class);
    }

}