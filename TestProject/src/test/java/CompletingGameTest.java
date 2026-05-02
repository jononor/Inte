package test.java;

import org.example.InputReader;
import org.example.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class CompletingGameTest {

    private InputStream standardSystemIn;
    private PrintStream standardSystemOut;

    @BeforeEach
    void storeStandardSystemIn() {
        standardSystemIn = System.in;
        standardSystemOut = System.out;
    }


    @AfterEach
    void restoreStandardSystemIn() {
        System.setIn(standardSystemIn);
        System.setOut(standardSystemOut);
    }


    @Test
    void completingTheGame_Closing_TheGame() {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Allt under görs i enterState() metoden
        CompletingGame gameFinish = new CompletingGame(new InputReader(), new StateMachine());
        List<String> choices = gameFinish.getChoices(new ArrayList<>());
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
        int result = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = gameFinish.getNextState(result);

        assertEquals(StateMachine.States.COMPLETING_GAME, nextState);
    }

    @Test
    void completingTheGame_Restarting_TheGame() {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Allt under görs i enterState() metoden
        CompletingGame gameFinish = new CompletingGame(new InputReader(), new StateMachine());
        List<String> choices = gameFinish.getChoices(new ArrayList<>());
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
        int result = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = gameFinish.getNextState(result);

        assertEquals(StateMachine.States.HUBWORLD, nextState);

    }



}
