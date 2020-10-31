package ru.ideaplatform.flights.controller.shell;

import lombok.AllArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Help;
import ru.ideaplatform.flights.exception.NoDataException;
import ru.ideaplatform.flights.exception.ThereIsNoSuchFlightException;
import ru.ideaplatform.flights.service.FlightService;
import ru.ideaplatform.flights.utils.ConsoleUtils;

@SuppressWarnings("unused")
@AllArgsConstructor
@ShellComponent
public class ShellCommands implements PromptProvider, Help.Command {

    private final FlightService service;

    static {
        System.out.println(ConsoleUtils.help());
    }

    @ShellMethod(value = "Load data from file", key = {"l", "load"})
    public String load(@ShellOption(defaultValue = "") String path) {
        try {
            return ConsoleUtils.listToString(service.load(path));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Find lowest price of flight")
    public String min(@ShellOption(defaultValue = "") String from, @ShellOption(defaultValue = "") String to) {
        try {
            return service.findLowestPrice(from, to).toString();
        } catch (ThereIsNoSuchFlightException | NoDataException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Find lowest price of flight")
    public String max(@ShellOption(defaultValue = "") String from, @ShellOption(defaultValue = "") String to) {
        try {
            return service.findHighestPrice(from, to).toString();
        } catch (ThereIsNoSuchFlightException | NoDataException e) {
            return e.getMessage();
        }
    }

    @ShellMethod(value = "Custom help information", key = {"h", "help"})
    public String help() {
        return ConsoleUtils.help();
    }

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("flights:>>");
    }
}
