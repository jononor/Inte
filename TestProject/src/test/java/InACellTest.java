package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InACellTest {

    private InputReader mockInputReader;
    private StateMachine stateMachine;
    private InACell inACell;
    private Map<Point, Cell> cells;

    private StateMachine getNewStateMachine(InputReader inputReader) {
        StateMachine stateMachine = new StateMachine(inputReader);

        HubWorld hubWorld = new HubWorld(inputReader, stateMachine);
        TakingExam takingExam = new TakingExam(inputReader, stateMachine);
        CompletingGame completingGame = new CompletingGame(inputReader, stateMachine);
        PickingArea pickingArea = new PickingArea(inputReader, stateMachine);
        InACell inACell = new InACell(inputReader, stateMachine);

        Map<StateMachine.States, State> allStates = new HashMap<>();
        allStates.put(StateMachine.States.HUBWORLD, hubWorld);
        allStates.put(StateMachine.States.TAKING_EXAM, takingExam);
        allStates.put(StateMachine.States.COMPLETING_GAME, completingGame);
        allStates.put(StateMachine.States.PICKING_AREA, pickingArea);
        allStates.put(StateMachine.States.IN_A_CELL, inACell);

        stateMachine.setAllStates(allStates);

        this.inACell = inACell;
        return stateMachine;
    }

    @BeforeEach
    void setUp() {
        mockInputReader = Mockito.mock(InputReader.class);
        stateMachine = getNewStateMachine(mockInputReader);

        cells = new HashMap<>();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                cells.put(new Point(x, y), new Cell("Cell " + x + "," + y, new Point(x, y)));
            }
        }

        GridMap gridMap = new GridMap(cells, 9, 9);
        inACell.setCurrentMap(gridMap);

        Point center = new Point(4, 4);
        Cell centerCell = cells.get(center);
        stateMachine.getPlayer().moveTo(centerCell);
    }

    @Test
    public void testChoicesWithoutEnemy() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(6, choices.size());
        assertEquals("Inspektera Rummet", choices.get(0));
        assertEquals("Ro NORR", choices.get(1));
        assertEquals("Ro ÖST", choices.get(2));
        assertEquals("Ro SYD", choices.get(3));
        assertEquals("Ro VÄST", choices.get(4));
        assertEquals("Ta Tunnelbanan Hem", choices.get(5));
    }

    @Test
    public void testChoicesWithEnemy() {
        Enemy enemy = new Enemy("Demon", 1);
        enemy.moveTo(cells.get(new Point(4, 4)));

        List<String> choices = inACell.getChoices(List.of());
        assertEquals(7, choices.size());
        assertEquals("Anfall Demon", choices.get(0));
        assertEquals("Inspektera Rummet", choices.get(1));
        assertEquals("Ro NORR", choices.get(2));
        assertEquals("Ro ÖST", choices.get(3));
        assertEquals("Ro SYD", choices.get(4));
        assertEquals("Ro VÄST", choices.get(5));
        assertEquals("Ta Tunnelbanan Hem", choices.get(6));
    }

    @Test
    public void testMovementNorth() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(StateMachine.States.IN_A_CELL, inACell.getNextState(2));
    }

    @Test
    public void testMovementEast() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(StateMachine.States.IN_A_CELL, inACell.getNextState(3));
    }

    @Test
    public void testMovementSouth() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(StateMachine.States.IN_A_CELL, inACell.getNextState(4));
    }

    @Test
    public void testMovementWest() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(StateMachine.States.IN_A_CELL, inACell.getNextState(5));
    }

    @Test
    public void testTakeSubwayHome() {
        List<String> choices = inACell.getChoices(List.of());
        assertEquals(StateMachine.States.HUBWORLD, inACell.getNextState(6));
    }


}
