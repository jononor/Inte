package org.example;

import org.example.Cell;
import org.example.Damage;
import org.example.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private final String VALID_NAME = "Player";
    private final Cell SENTINELCELL = new Cell("SENTINEL CELL", new Point(999, 999));
    private Player player;


    @ParameterizedTest
    @ValueSource(strings = {"Player123", " ", ",", "13", ""})
    void invalidPlayerNameThrowsException(String invalidName) {
        assertThrows(IllegalArgumentException.class, () -> new Player(invalidName));

    }

    @BeforeEach
    void setUp() {
        player = new Player(VALID_NAME);
    }

    @Test
    void playerCreated() {
        assertNotNull(new Player(VALID_NAME), "object created");
    }

    @Test
    void playerNameIsFormatted() {
        assertEquals("Player", new Player("Player").getName());
        assertEquals("Player", new Player("player").getName());
        assertEquals("Player", new Player("  player").getName());
        assertEquals("Player", new Player("player  ").getName());
        assertEquals("Player", new Player("   player   ").getName());
    }

    @Test
    void nullPlayerNameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Player(null));
    }

    @Test
    void playerCreatedWithCorrectStats() {
        assertEquals("Player", player.getName(), "Player name not correct");
        assertEquals(5, player.getDamage().getValue(), "Player damage not correct");
        assertEquals(15, player.getMaxHealth(), "Player max health not correct");
        assertEquals(15, player.getCurrentHealth(), "Player current health not correct");
        assertEquals(1, player.getLevel(), "Player level incorrect");
    }

    @Test
    void playerCurrentHealthChangeMinus10() {
        assertEquals(15, player.getCurrentHealth(), "Player current health not correct");
        assertEquals(15, player.getCurrentHealth(), "Player current health not correct");
        player.addCurrentHealth(-10);
        assertEquals(5, player.getCurrentHealth(), "Player current health not correct after taking damage");
        assertEquals(15, player.getMaxHealth(), "Player max health not correct after taking damage");
    }

    @Test
    void playerMaxHealthIsNotIncreasedAboveMaxValue() {
        assertEquals(15, player.getMaxHealth());
        assertEquals(15, player.getCurrentHealth());
        player.addCurrentHealth(10);
        assertEquals(15, player.getCurrentHealth());
        assertEquals(15, player.getMaxHealth());
    }

    @Test
    void playerHealthDecreasedBelowZeroMakesCurrentHealthZero() {
        player.addCurrentHealth(-20);
        assertEquals(0, player.getCurrentHealth());
    }

    @Test
    void playerHeal5HP(){
        assertEquals(15, player.getCurrentHealth());
        player.addCurrentHealth(-10);
        assertEquals(5, player.getCurrentHealth());
        player.addCurrentHealth(10);
        assertEquals(15, player.getCurrentHealth());
    }


    /**
     * Tests how random critical strikes are, should range between 0%<critical strikes<20%
     * Questionable test but may show that the rate is not too far off
     */
    @Test
    void CriticalStrikeBadLuckProtection() {
        int criticalHits = 0;

        for (int i = 0; i < 10000; i++) {
            Damage damage = player.getDamage();
            if (damage.getValue() == 10) {
                criticalHits++;
            }
        }

        double rate = (double) criticalHits / 10000;
        assertTrue(rate >= 0 && rate <= 0.2, String.format("Crit rate was %f but should have been %s", rate, "between 0-0.02"));
    }

    @Test
    void badLuckProtectorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.addBadLuckProtector(0));
    }

    @Test
    void playerLevelUpByOne() {
        assertEquals(1, player.getLevel());
        player.increaseLevel();
        assertEquals(2, player.getLevel());
    }

    @Test
    void playerLevelUpIncreaseStats() {
        player.increaseLevel();
        assertEquals(30, player.getMaxHealth());
        assertEquals(10, player.getDamage().getValue());
    }

    @Test
    void playerCurrentHealthIncreasedToMaxHealthWhenLevelingUp() {
        player.addCurrentHealth(-5);
        assertEquals(10, player.getCurrentHealth(), "current HP after taking 5 damage");
        player.increaseLevel();
        assertEquals(30, player.getCurrentHealth(), "current HP after leveling up");
    }

    @Test
    void getPlayerCell() {
        assertEquals(SENTINELCELL.getPosition(), new Player(VALID_NAME).getCell().getPosition());
    }

    @Test
    void playerToStringMethod() {
        assertEquals(String.format("Level: %d\nHP: %d/%d", player.getLevel(), player.getCurrentHealth(), player.getMaxHealth()), player.toString());
    }

    @Test
    void playerResetMethod() {
        player.addCurrentHealth(-20);
        player.reset();
        assertEquals(15, player.getCurrentHealth());
    }

    @Test
    void sentinelCellUponCreation() {
        assertEquals(player.getSENTINEL_CELL(), player.getCell());
    }

    @Test
    void moveToValidCell() {
        Cell validCell = new Cell(VALID_NAME, new Point(5, 2));
        player.moveTo(validCell);
        assertNotEquals(SENTINELCELL.getPosition(), player.getCell().getPosition());
        assertEquals(validCell, player.getCell());
    }

    @Test
    void actorIsMovedToSentinelCell() {
        Cell sentinelCell = player.getSENTINEL_CELL();
        Cell anotherCell = new Cell("Another Cell", new Point(5, 2));
        player.moveTo(anotherCell);
        assertNotEquals(sentinelCell, player.getCell());
        assertFalse(sentinelCell.containsPlayer());
        player.moveToSentinelCell();
        assertFalse(anotherCell.containsPlayer());
        assertTrue(sentinelCell.containsPlayer());
        assertEquals(sentinelCell, player.getCell());
    }

    @Test
    void moveToCellThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> player.moveTo(null));

    }
}
