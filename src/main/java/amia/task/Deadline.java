package amia.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import amia.exception.AmiaException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final DateTimeFormatter FILE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private LocalDateTime by;

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The description of the task.
     * @param byStr       The deadline in the format "yyyy-MM-dd HHmm".
     * @throws AmiaException If the date/time format is invalid.
     */
    public Deadline(String description, String byStr) throws AmiaException {
        super(description);
        try {
            this.by = LocalDateTime.parse(byStr, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new AmiaException("...Invalid date/time format. Use yyyy-MM-dd HHmm");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileString() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + by.format(FILE_FORMAT);
    }
}
