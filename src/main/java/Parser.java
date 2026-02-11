public class Parser {

    public static CommandType parseCommandType(String input) throws AmiaException {
        if (input == null || input.trim().isEmpty()) {
            throw new AmiaException("...?");
        }
        CommandType cmdType = CommandType.fromString(input.trim().toLowerCase());
        return cmdType;
    }

    public static String extractDescription(String command, String keyword) throws AmiaException {
        String desc = command.substring(keyword.length()).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("... The description of a task cannot be empty...");
        }
        return desc;
    }

    public static String extractIndexArg(String command, String keyword) throws AmiaException {
        String args = command.substring(keyword.length()).trim();
        if (args.isEmpty()) {
            throw new AmiaException("... Invalid format... Use: " + keyword + " <index>");
        }
        return args;
    }

    public static int parseIndex(String indexStr) throws AmiaException {
        try {
            return Integer.parseInt(indexStr) - 1;
        } catch (NumberFormatException e) {
            throw new AmiaException("... Invalid task number...");
        }
    }

    public static DeadlineInfo parseDeadline(String command) throws AmiaException {
        String args = command.substring(8).trim();
        int byIdx = args.lastIndexOf("/by");
        if (byIdx == -1 || args.isEmpty()) {
            throw new AmiaException("Invalid format... Use: deadline <desc> /by <date>");
        }
        String desc = args.substring(0, byIdx).trim();
        String by = args.substring(byIdx + 3).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("... The description of a task can't be empty...");
        }
        if (by.isEmpty()) {
            throw new AmiaException("... The deadline can't be empty...");
        }
        return new DeadlineInfo(desc, by);
    }

    public static EventInfo parseEvent(String command) throws AmiaException {
        String args = command.substring(5).trim();
        int fromIdx = args.lastIndexOf("/from");
        int toIdx = args.lastIndexOf("/to");
        if (fromIdx == -1 || toIdx == -1 || args.isEmpty()) {
            throw new AmiaException("Invalid format... Use: event <desc> /from <start> /to <end>");
        }
        String desc = args.substring(0, fromIdx).trim();
        String from = args.substring(fromIdx + 5, toIdx).trim();
        String to = args.substring(toIdx + 3).trim();
        if (desc.isEmpty()) {
            throw new AmiaException("...The description of a task can't be empty...");
        }
        if (from.isEmpty()) {
            throw new AmiaException("... The start time can't be empty...");
        }
        if (to.isEmpty()) {
            throw new AmiaException("... The end time can't be empty...");
        }
        return new EventInfo(desc, from, to);
    }

    public static class DeadlineInfo {
        public String description;
        public String deadline;

        public DeadlineInfo(String description, String deadline) {
            this.description = description;
            this.deadline = deadline;
        }
    }

    public static class EventInfo {
        public String description;
        public String from;
        public String to;

        public EventInfo(String description, String from, String to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }
    }
}
