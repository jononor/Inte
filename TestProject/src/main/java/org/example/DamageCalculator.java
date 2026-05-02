package org.example;

public class DamageCalculator {
    private static final int DAMAGE_MULTIPLIER = 5;
    private static final int CRITICAL_MULTIPLIER = 2;
    private static final double CRITICAL_STRIKE_CHANCE = 0.02;

    public static Damage calculateDamage(Actor actor) {
        boolean isCrit = RandomNumberGenerator.nextDouble() <= CRITICAL_STRIKE_CHANCE * actor.getBadLuckProtector();
        if (!isCrit) {
            actor.addBadLuckProtector(1);
        }
        int damageValue = actor.getLevel() * DAMAGE_MULTIPLIER;
        if (isCrit) {
            damageValue *= CRITICAL_MULTIPLIER;
            actor.addBadLuckProtector(-1);
        }
        return new Damage(damageValue, actor.getName(), isCrit);

    }
}
