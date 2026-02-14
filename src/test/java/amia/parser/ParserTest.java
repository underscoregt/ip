package amia.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import amia.exception.AmiaException;

public class ParserTest {

    @Test
    public void testParseCommandType() throws AmiaException {
        CommandType type1 = Parser.parseCommandType("todo read book");
        assertEquals(CommandType.TODO, type1);

        CommandType type2 = Parser.parseCommandType("list");
        assertEquals(CommandType.LIST, type2);

        CommandType type3 = Parser.parseCommandType("bye");
        assertEquals(CommandType.BYE, type3);
    }

    @Test
    public void testExtractDescription() throws AmiaException {
        String desc = Parser.extractDescription("todo read a book", "todo");
        assertEquals("read a book", desc);

        String desc2 = Parser.extractDescription("deadline submit project /by 2024-02-15", "deadline");
        assertEquals("submit project /by 2024-02-15", desc2);
    }

    @Test
    public void testParseIndex() throws AmiaException {
        int idx1 = Parser.parseIndex("1");
        assertEquals(0, idx1);

        int idx2 = Parser.parseIndex("5");
        assertEquals(4, idx2);

        assertThrows(AmiaException.class, () -> {
            Parser.parseIndex("abc");
        });
    }
}
