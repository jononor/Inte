package org.example;

public class Player extends Actor {


    public Player(String name) {
        super(name, 1);
    }

    public void increaseLevel() {
        super.increaseLevel();
    }

    public void reset() {
        super.setCurrentHealth(super.getMaxHealth());
        super.moveToSentinelCell();
    }
}