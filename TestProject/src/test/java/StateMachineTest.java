package test.java;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class StateMachineTest {


    @Test
    public void TestHubWorld(){
        InputReader mockInputReader = Mockito.mock(InputReader.class);
        when(mockInputReader.getUserChoice(anyList())).thenReturn(1,1,2,9,3,2,8,9,-1);
        StateMachine stateMachine = new StateMachine(mockInputReader);

        Map<StateMachine.States, State> allStates = new HashMap<>();
        allStates.put(StateMachine.States.HUBWORLD, new HubWorld(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.TAKING_EXAM, new TakingExam(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.COMPLETING_GAME, new CompletingGame(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.PICKING_AREA, new PickingArea(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.IN_A_CELL, new InACell(mockInputReader, stateMachine));

        stateMachine.setAllStates(allStates);

        try {
            stateMachine.changeState(StateMachine.States.HUBWORLD);
        } catch (InputMismatchException e) {
            System.out.println("RuttAvklarad!");
        }


    }

    @Test
    public void testPickingArea() {
        InputReader mockInputReader = Mockito.mock(InputReader.class);
        when(mockInputReader.getUserChoice(anyList()))
                .thenReturn(
                        2, 2, 1,
                        2, 3, 1,
                        2, 4, 1,
                        2, 5, 1,
                        2, 6, 1,
                        2, 7, 1,
                        2, 8, 1,
                        2, 9, 1,1,
                        -1
                );


        StateMachine stateMachine = new StateMachine(mockInputReader);

        State mockInACell = Mockito.mock(State.class, withSettings()
                .useConstructor(mockInputReader, stateMachine)
                .defaultAnswer(CALLS_REAL_METHODS));

        when(mockInACell.getChoices(anyList())).thenReturn(List.of("Gå", "Dö", "Teleportera"));
        when(mockInACell.getNextState(anyInt())).thenReturn(StateMachine.States.PICKING_AREA);

        Map<StateMachine.States, State> allStates = new HashMap<>();
        allStates.put(StateMachine.States.HUBWORLD, new HubWorld(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.TAKING_EXAM, new TakingExam(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.COMPLETING_GAME, new CompletingGame(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.PICKING_AREA, new PickingArea(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.IN_A_CELL, mockInACell);

        stateMachine.setAllStates(allStates);

        try {
            stateMachine.changeState(StateMachine.States.HUBWORLD);
        } catch (InputMismatchException e) {
            System.out.println("RuttAvklarad!");
        }
    }

    @Test
    public void testStateMachinePath3() {
        InputReader mockInputReader = Mockito.mock(InputReader.class);
        when(mockInputReader.getUserChoice(anyList())).thenReturn(2, 2, 1, 1, 1, 2, -1);

        StateMachine stateMachine = new StateMachine(mockInputReader);

        State mockInACell = Mockito.mock(State.class, withSettings()
                .useConstructor(mockInputReader, stateMachine)
                .defaultAnswer(CALLS_REAL_METHODS));

        when(mockInACell.getChoices(anyList())).thenReturn(List.of("Gå", "Dö", "Teleportera"));
        when(mockInACell.getNextState(1)).thenReturn(StateMachine.States.IN_A_CELL);
        when(mockInACell.getNextState(2)).thenReturn(StateMachine.States.HUBWORLD);
        when(mockInACell.getNextState(3)).thenReturn(StateMachine.States.HUBWORLD);

        Map<StateMachine.States, State> allStates = new HashMap<>();
        allStates.put(StateMachine.States.HUBWORLD, new HubWorld(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.TAKING_EXAM, new TakingExam(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.COMPLETING_GAME, new CompletingGame(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.PICKING_AREA, new PickingArea(mockInputReader, stateMachine));
        allStates.put(StateMachine.States.IN_A_CELL, mockInACell);

        stateMachine.setAllStates(allStates);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try {
            stateMachine.changeState(StateMachine.States.HUBWORLD);
        } catch (InputMismatchException e) {
            System.out.println("RuttAvklarad!");
        }

        String output = outputStream.toString().replace("\r", "").trim();

        String expectedSequence = String.join("\n",
                "HUBWORLD",
                "PICKING_AREA",
                "IN_A_CELL",
                "IN_A_CELL",
                "IN_A_CELL",
                "IN_A_CELL",
                "HUBWORLD",
                "RuttAvklarad!"
        );

        assertTrue(output.contains(expectedSequence),
                "Expected sequence not found in output. Actual output:\n" + output);
    }

    @Test
    void currentAreaReturnsArea(){
        InputReader mockInputReader = Mockito.mock(InputReader.class);
        when(mockInputReader.getUserChoice(anyList())).thenReturn(2, 2, 1, 1, 1, 2, -1);
        StateMachine stateMachine = new StateMachine(mockInputReader);

        Object result = stateMachine.getCurrentArea();
        assertTrue(result instanceof Area);
    }
}
