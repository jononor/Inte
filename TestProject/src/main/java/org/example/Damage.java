package org.example;

public class Damage {
    private final int DAMAGE;
    private final String NAME;
    private final boolean CRITICAL;

    public Damage(int damage, String name, boolean critical) {
        if (critical) {
            this.DAMAGE = damage;
            this.NAME = name;
            this.CRITICAL = true;
        } else {
            this.DAMAGE = damage;
            this.NAME = name;
            this.CRITICAL = false;
        }
    }

    public int getValue() {
        return DAMAGE;
    }

    public String toString() {
        if (CRITICAL) {
            return String.format(("%s critical strike for %d damage!"), NAME, DAMAGE);
        }
        return String.format("%s deals %d damage", NAME, DAMAGE);

    }
}