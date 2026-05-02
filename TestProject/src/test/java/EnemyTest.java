package test.java;

import org.example.Enemy;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EnemyTest {

    @Test
    void hostileEnemiesExist() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        int hostile = 0;

        for (int i = 0; i < 100; i++) {
            enemies.add(new Enemy("Enemy", 5));
        }

        for (Enemy e : enemies) {
            if (e.isHostile()) {
                hostile++;
            }
        }

        double hostileRate = hostile / (double) 100;
        assertTrue(hostileRate >= 0.3 && hostileRate <= 0.7, String.format("Hostile rate was %f but should have been %s", hostileRate, "between 0.3-0.7"));

    }
}