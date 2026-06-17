package org.example;

import org.example.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    private Direction east;
    private Direction west;
    private Direction north;
    private Direction south;

    @BeforeEach
    public void setUp() {
        east = new Direction(new Point(1, 0));
        west = new Direction(new Point(-1, 0));
        north = new Direction(new Point(0, -1));
        south = new Direction(new Point(0, 1));
    }

    @Test
    void allDirectionsHaveCorrectName() {
        assertEquals("East", east.getName());
        assertEquals("West", west.getName());
        assertEquals("North", north.getName());
        assertEquals("South", south.getName());
        assertEquals("South", south.toString());
    }

    @Test
    void incorrectDirectionThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Direction(new Point(5, -1));
        });
    }
    @Test
    void incorrectDirectionsThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {new Direction(new Point(0, -3));});
        assertThrows(IllegalArgumentException.class, () -> {new Direction(new Point(-1, 4));});
        assertThrows(IllegalArgumentException.class, () -> {new Direction(new Point(1, 5));});


    }

    @Test
    void equalsIsEqual() {
        assertEquals(east, east);
    }

    @Test
    void equalsIsNotEqual() {
        assertNotEquals(east, west);
    }

    @Test
    void equalsIsNotOfTheSameType() {
        assertNotEquals(east, null);
    }

    @Test
    void getPointReturnsCorrectPoint() {
        Point point = new Point(1, 0);
        assertEquals(point, east.getPoint());
    }

    @Test
    void hashCodeIsEqual() {
        assertEquals(east.hashCode(), east.hashCode());
    }
}