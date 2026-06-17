package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.example.InputReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.lang.model.type.ArrayType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputReaderTest {
	
	private InputStream standardSystemIn;
    private PrintStream standardSystemOut;

    //Håll noga koll på!
	@BeforeEach
	void storeStandardSystemIn() {
		standardSystemIn = System.in;
        standardSystemOut = System.out;
	}

    //Håll noga koll på!
	@AfterEach
	void restoreStandardSystemIn() {
		System.setIn(standardSystemIn);
        System.setOut(standardSystemOut);
	}
	
	@Test
	void returnsChoiceValue() {
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        List<String> choices = Arrays.asList("Attack", "Move", "Wait");
        int choiceValue = new InputReader().getUserChoice(choices);
		assertEquals(3, choiceValue);
	}
	
	@Test
	void choiceIsInsideBounds() {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        List<String> choices = Arrays.asList("Attack", "Move", "Wait");
		int choiceValue = new InputReader().getUserChoice(choices);
		assertTrue(choiceValue > 0 && choiceValue <= choices.size());
	}

    @Test
    void choiceIsUnderBounds() {
        System.setIn(new ByteArrayInputStream("0\n2\n".getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        new InputReader().getUserChoice(Arrays.asList("Attack", "Move", "Wait"));
        assertEquals("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! ILLEGALARGUMENT"+System.lineSeparator(), output.toString());
    }

    @Test
    void choiceIsOverBounds() {
        System.setIn(new ByteArrayInputStream("4\n2\n".getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        new InputReader().getUserChoice(Arrays.asList("Attack", "Move", "Wait"));
        assertEquals("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! ILLEGALARGUMENT"+System.lineSeparator(), output.toString());
    }
	

	@Test
	void exceptionWhenInputIsString() {
        System.setIn(new ByteArrayInputStream("name\n2\n".getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        new InputReader().getUserChoice(Arrays.asList("Attack", "Move", "Wait"));
        assertEquals("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! INPUTMISMATCH"+System.lineSeparator(), output.toString());
	}

    @Test
    void exceptionWhenInputIsDouble() {
        System.setIn(new ByteArrayInputStream("1.5\n2\n".getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        new InputReader().getUserChoice(Arrays.asList("Attack", "Move", "Wait"));
        assertEquals("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! INPUTMISMATCH"+System.lineSeparator(), output.toString());
    }

    @Test
    void exceptionWhenInputAreCharacters() {
        System.setIn(new ByteArrayInputStream("@[<)\n2\n".getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        new InputReader().getUserChoice(Arrays.asList("Attack", "Move", "Wait"));
        assertEquals("Vänligen ange ett svarsnummer inom ramen för möjliga svarsalternativ! INPUTMISMATCH"+System.lineSeparator(), output.toString());
    }
}