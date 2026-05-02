package test.java;

import org.example.Combat;
import org.example.Enemy;
import org.example.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CombatTest {
    private Player player;
    private Enemy enemy;

    @BeforeEach
    public void setUp() {
        player = new Player("Player");
        enemy = new Enemy("EnemyOne", 1);
    }

    @Test
    void playerSurvives(){
        assertTrue(Combat.engageCombat(player, enemy));
    }

    @Test
    void playerDies(){
        Enemy enemy = new Enemy("Enemy", 8);
        assertFalse(Combat.engageCombat(player, enemy));
    }

    //sometimes fail when player crit and defeat two enemies in a row
    //or enemy crit and kill player on the first fight
    @Test
    void playerDiesAfterTwoFightsWithoutHealing(){
        Combat.engageCombat(player, enemy);
        enemy.setCurrentHealth(enemy.getMaxHealth());
        assertFalse(Combat.engageCombat(player, enemy));
    }

    @Test
    void combatWithDeadUnitThrowsException(){
        Combat.engageCombat(player, enemy);
        assertThrows(IllegalArgumentException.class, () -> {Combat.engageCombat(player, enemy);});
        Enemy enemy2 = new Enemy("enemyTwo", 1);
        Combat.engageCombat(player, enemy2);
        assertThrows(IllegalArgumentException.class, () -> {Combat.engageCombat(player, enemy2);});
    }
}
