package org.example;

import java.awt.*;

public abstract class Actor {
    private final int HEALTH_MULTIPLIER = 15;
    private final Cell SENTINEL_CELL = new Cell("Sentinel cell", new Point(999, 999));
    private String name;
    private int maxHealth;
    private int currentHealth;
    private int level;
    private Cell cell;
    private int badLuckProtector = 0;

    protected Actor(String name, int level) {
        validateName(name);
        name = formatName(name);
        this.name = name;
        this.level = level;
        this.maxHealth = HEALTH_MULTIPLIER * level;
        this.currentHealth = maxHealth;
        this.cell = SENTINEL_CELL;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getBadLuckProtector() {
        return badLuckProtector;
    }

    /**
     * Uses bad luck protection, increase critical strike chance by 100% each time a non-critical strike is made.
     * First hit and first after a critical strike always has a 0% critical strike chance.
     *
     * @return
     */
    public Damage getDamage() {
        return DamageCalculator.calculateDamage(this);
    }

    public Cell getCell() {
        return cell;
    }

    public Cell getSENTINEL_CELL() {
        return SENTINEL_CELL;
    }

    /**
     * Moves character to a specific cell
     *
     * @param cell the target cell
     */
    public void moveTo(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("Cell is null");
        }
        this.cell.exitCell(this);
        cell.enterCell(this);
        this.cell = cell;
    }

    public void moveToSentinelCell() {
        moveTo(SENTINEL_CELL);
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    /**
     * Changes current health value based on parameter, below 0 will result in death.
     *
     * @param value Integer change, negative or positive.     *
     */
    public void addCurrentHealth(int value) {
        if (value < 0) {
            if (currentHealth + value < 0) {
                currentHealth = 0;
            } else {
                currentHealth += value;
            }
        } else {
            if (currentHealth + value > maxHealth) {
                currentHealth = maxHealth;
            } else {
                currentHealth += value;
            }
        }
    }

    /**
     * if value is positive, increments bad luck protector by 1,
     * if value is negative resets bad luck protection (set value to 0).
     * *
     *
     * @param value
     * @Throws 0 generate an IllegalArgumentException
     */
    public void addBadLuckProtector(int value) {
        if (value == 0) {
            throw new IllegalArgumentException("value can not be 0");
        }
        if (value < 0) {
            badLuckProtector = 0;
        } else {
            badLuckProtector++;
        }
    }

    public String toString() {
        return String.format("Level: %d\nHP: %d/%d", level, currentHealth, maxHealth);
    }

    protected void increaseLevel() {
        this.level++;
        increaseHealthUponLevelUp();
    }

    private void increaseHealthUponLevelUp() {
        this.maxHealth = level * HEALTH_MULTIPLIER;
        this.currentHealth = maxHealth;
    }

    private String formatName(String name) {
        name = name.trim().toLowerCase();
        return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
    }

    private IllegalArgumentException validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Can not be empty");
        }
        for (char c : name.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                throw new IllegalArgumentException("Name contains non-letters");
            }
        }
        return null;
    }
}