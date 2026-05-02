package test.java;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GridMapTest {

    private GridMap grid;
    private Cell start;
    private Cell east;
    private Cell south;
    private Player player;
    private Enemy enemy;

    private ArrayList<Direction> allDirections = new ArrayList<>(List.of(
            new Direction(new Point(0, -1)), // North
            new Direction(new Point(0, 1)),  // South
            new Direction(new Point(-1, 0)), // West
            new Direction(new Point(1, 0))   // East
    ));

    @BeforeEach
    public void setUp() {
        start = new Cell("Start", new Point(0, 0));
        east = new Cell("East", new Point(1, 0));
        south = new Cell("South", new Point(0, 1));

        HashMap<Point, Cell> map = new HashMap<>();
        map.put(start.getPosition(), start);
        map.put(east.getPosition(), east);
        map.put(south.getPosition(), south);

        grid = new GridMap(map, 2, 2);

        player = new Player("Hero");
        start.enterCell(player);
        player.moveTo(start);

        enemy = new Enemy("He",3);
        east.enterCell(enemy);
        enemy.moveTo(east);
    }

    @Test
    public void testGetAvailableDirectionsCell() {
        ArrayList<Direction> directions = grid.getAvailableDirections(start);
        assertEquals(2, directions.size());
        ArrayList<String> names = new ArrayList<>();
        for (Direction d : directions) names.add(d.toString());
        assertTrue(names.contains("East"));
        assertTrue(names.contains("South"));
    }

    @Test
    public void testGetAvailableDirectionsActor() {
        ArrayList<Direction> directions = grid.getAvailableDirections(player);
        assertEquals(2, directions.size());
        ArrayList<String> names = new ArrayList<>();
        for (Direction d : directions) names.add(d.toString());
        assertTrue(names.contains("East"));
        assertTrue(names.contains("South"));
    }

    @Test
    public void testActorMovementForPlayerAndEnemy() {
        ArrayList<Direction> available = grid.getAvailableDirections(player);
        for (Direction d : allDirections) {
            if (!available.contains(d)) {
                assertThrows(IllegalArgumentException.class, () -> grid.moveActorInDirection(player, d));
            } else {
                grid.moveActorInDirection(player, d);
                Point expected = player.getCell().getPosition();
                assertEquals(expected, grid.getActorPosition(player));
                player.moveTo(start);
                enemy.moveTo(player.getCell());
                grid.moveActorInDirection(enemy, d);
            }
        }
    }

    @Test
    public void testMoveActorToOrigin() {
        player.moveTo(east);
        grid.moveActorToOrigin(player);
        assertEquals(new Point(0, 0), player.getCell().getPosition());

        enemy.moveTo(south);
        grid.moveActorToOrigin(enemy);
        assertEquals(new Point(0, 0), enemy.getCell().getPosition());
    }

    @Test
    public void testToStringNotEmpty() {
        String str = grid.toString();
        assertTrue(str.contains("0"));
        assertTrue(str.contains("_") || str.contains("?"));
    }

    @Test
    public void testToStringEmptyMap() {
        HashMap<Point, Cell> map = new HashMap<>();
        GridMap empty = new GridMap(map, 0, 0);
        assertEquals("[Empty Map]", new GridMap(new HashMap<>(), 0, 0).toString());
    }

    @Test
    public void testGetBookPositionAndToStringFullyExplored() {
        assertNull(grid.getBookPosition());
        start.placeBook();
        assertEquals(start.getPosition(), grid.getBookPosition());
        String s = grid.toStringFullyExplored();
        assertTrue(s.contains("B"));
    }

    @Test
    public void testToStringFullyExploredEmptyMap() {
        HashMap<Point, Cell> map = new HashMap<>();
        GridMap empty = new GridMap(map, 0, 0);
        assertEquals("[Empty Map]", empty.toStringFullyExplored());
    }
}
