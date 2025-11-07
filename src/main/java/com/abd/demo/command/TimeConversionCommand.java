package com.abd.demo.command;

import com.abd.demo.adapter.OutputAdapter;
import com.abd.demo.domain.Time;
import com.abd.demo.service.TimeConverterService;
import com.abd.demo.service.TimeParser;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * Command to convert time input to British spoken format.
 * Single Responsibility: Handle time conversion requests.
 * This is the default/fallback command.
 */
@Slf4j
public class TimeConversionCommand implements Command {
    private final TimeParser timeParser;
    private final TimeConverterService timeConverterService;
    private final OutputAdapter output;

    public TimeConversionCommand(TimeParser timeParser,
                                 TimeConverterService timeConverterService,
                                 OutputAdapter output) {
        this.timeParser = timeParser;
        this.timeConverterService = timeConverterService;
        this.output = output;
        log.debug("TimeConversionCommand initialized");
    }

    @Override
    public boolean canHandle(String input) {
        return true; // Default handler - accepts all input
    }

    @Override
    public CommandResult execute(String input) {
        log.info("Executing time conversion for input: '{}'", input);
        parseTime(input)
            .map(time -> {
                String result = timeConverterService.convert(time);
                log.info("Successfully converted input '{}' to '{}'", input, result);
                return result;
            })
            .ifPresentOrElse(
                output::showResult,
                () -> log.warn("Time conversion failed for input: '{}'", input)
            );
        return CommandResult.continueRunning();
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE; // Lowest priority (fallback)
    }

    private Optional<Time> parseTime(String input) {
        try {
            log.debug("Parsing time input: '{}'", input);
            Time time = timeParser.parse(input);
            log.debug("Successfully parsed time: {}", time);
            return Optional.of(time);
        } catch (IllegalArgumentException e) {
            log.error("Failed to parse time input '{}': {}", input, e.getMessage());
            output.showError(e.getMessage());
            return Optional.empty();
        }
    }
}