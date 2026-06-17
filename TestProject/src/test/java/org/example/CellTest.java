package org.example;

import org.example.Cell;
import org.example.Enemy;
import org.example.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    private Cell cell;
    private Player player;
    private Enemy enemy;

    @BeforeEach
    void setUp() {
        cell = new Cell("A cold dungeon chamber",new Point());
        player = new Player("Hero");
        enemy = new Enemy("Goblin",1);
    }

    @Test
    void testConstructorSetsDescription() {
        assertEquals("Du befinner dig i A cold dungeon chamber", cell.getAreaDescription());
        assertFalse(cell.containsBook());
        assertFalse(cell.containsPlayer());
        assertFalse(cell.containsEnemy());
    }

    @Test
    void testPlaceBook() {
        assertFalse(cell.containsBook());
        cell.placeBook();
        assertTrue(cell.containsBook());
    }

    @Test
    void testEnterCellAddsPlayerAndMarksExplored() {
        cell.enterCell(player);
        assertTrue(cell.containsPlayer());
        assertTrue(cell.isExplored());
    }

    @Test
    void testEnterCellAddsEnemyDoesNotMarkExplored() {
        cell.enterCell(enemy);
        assertTrue(cell.containsEnemy());
        assertFalse(cell.isExplored());
    }

    @Test
    void containsPlayerWithEnemy() {
        cell.enterCell(enemy);
        assertFalse(cell.containsPlayer());
    }
    @Test
    void getPositionReturnsPosition() {
        cell.getPosition();
        assertEquals(cell.getPosition(),new Point(0,0));
    }
    @Test
    void doesGetEnemyReturnEnemyElseReturnException() {
        cell.enterCell(player);
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> cell.getEnemy());
        assertEquals("No enemy found", exception.getMessage());
        cell.enterCell(enemy);
        assertEquals(enemy, cell.getEnemy());
    }


    @Test
    void containsEnemyWithPlayer() {
        cell.enterCell(player);
        assertFalse(cell.containsEnemy());
    }

    @Test
    void testExitCellRemovesActor() {
        cell.enterCell(enemy);
        assertTrue(cell.containsEnemy());
        assertTrue(cell.exitCell(enemy));
        assertFalse(cell.containsEnemy());
    }

    @Test
    void methodReturnsEnemyUnit(){
        Enemy enemy = new Enemy("enemy", 2);
        enemy.moveTo(cell);
        assertEquals(enemy, cell.getEnemy());
    }

    @Test
    void testExitCellReturnsFalseIfActorNotPresent() {
        assertFalse(cell.exitCell(enemy));
    }
}
