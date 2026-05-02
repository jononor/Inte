package org.example;

public class Combat {
    public static boolean engageCombat(Player player, Enemy enemy) {
        if (player.getLevel() >= enemy.getLevel()) {
            if (combatCalculation(player, enemy) instanceof Player) {
                return true; //player survived
            } else {
                return false; //player died
            }
        }
        return (combatCalculation(enemy, player) instanceof Player); //player died
    }

    /**
     *
     * @param first  actor to attack
     * @param second actor to attack
     * @return actor that died
     */
    private static Actor combatCalculation(Actor first, Actor second) {
        while (first.getCurrentHealth() > 0 && second.getCurrentHealth() > 0) {
            int damage = first.getDamage().getValue();
            second.addCurrentHealth(-damage);
            if (second.getCurrentHealth() <= 0) {
                return first;
            }
            damage = second.getDamage().getValue();
            first.addCurrentHealth(-damage);

            if (first.getCurrentHealth() <= 0) {
                return second;
            }
        }
        throw new IllegalArgumentException("Incorrect actor entry");
    }
}
