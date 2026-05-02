package org.example;

public class Enemy extends Actor {
    private final boolean hostile;

    public Enemy(String name, int level) {
        super(name, level);
        this.hostile = RandomNumberGenerator.nextDouble() < 0.5;
    }

    public boolean isHostile() {
        return hostile;
    }
}
