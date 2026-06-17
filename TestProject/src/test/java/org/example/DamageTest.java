package org.example;

import org.example.Damage;
import org.example.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DamageTest {

    @Test
    void getDamageValue() {
        Player player = new Player("Player");
        assertEquals(5, player.getDamage().getValue());
    }

    @Test
    void toStringFormat() {
        Player player = new Player("Player");
        assertEquals("Player deals 5 damage", player.getDamage().toString());
    }

    @Test
    void toStringFormatCriticalStrike() {
        Player player = new Player("Player");
        Damage damage;
        boolean crit = false;
        while (crit == false) {
            damage = player.getDamage();
            if (damage.getValue() == 10) {
                crit = true;
                assertEquals("Player critical strike for 10 damage!", damage.toString());
            }
        }
    }
}
