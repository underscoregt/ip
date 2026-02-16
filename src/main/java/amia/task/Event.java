package amia.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import amia.exception.AmiaException;
import amia.exception.ErrorMessages;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs an Event task with the given description and time range.
     *
     * @param description The description of the event.
     * @param fromStr     The start time in the format "yyyy-MM-dd HHmm".
     * @param toStr       The end time in the format "yyyy-MM-dd HHmm".
     * @throws AmiaException If the date/time format is invalid.
     */
    public Event(String description, String fromStr, String toStr) throws AmiaException {
        super(description);
        try {
            this.from = LocalDateTime.parse(fromStr, INPUT_FORMAT);
            this.to = LocalDateTime.parse(toStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new AmiaException(ErrorMessages.INVALID_DATETIME_FORMAT);
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from.format(OUTPUT_FORMAT) + " to: " + to.format(OUTPUT_FORMAT)
                + ")";
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description + " | " + from.format(FILE_FORMAT) + " | "
                + to.format(FILE_FORMAT);
    }
}
