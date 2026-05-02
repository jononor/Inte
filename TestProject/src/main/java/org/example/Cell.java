package org.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Cell {
    private final Point position;
    private boolean book;
    private List<Actor> actors;
    private String description;
    private boolean explored;

    public Cell(String description, Point position) {
        this.description = description;
        this.position = position;
        this.actors = new ArrayList<>();
    }

    public Point getPosition() {
        return position;
    }

    public String getAreaDescription() {
        return String.format("Du befinner dig i %s",description);
    }

    public boolean containsPlayer() {
        for (Actor actor : actors) {
            if (actor instanceof Player) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEnemy() {
        for (Actor actor : actors) {
            if (actor instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    public Actor getEnemy(){
        for (Actor actor : actors) {
            if (actor instanceof Enemy){
                return actor;
            }
        }
        throw new NoSuchElementException("No enemy found");
    }

    public boolean containsBook() {
        return book;
    }

    public void placeBook() {
        this.book = true;
    }

    public boolean isExplored() {
        return explored;
    }

    public boolean enterCell(Actor actor) {
        if (actor instanceof Player) {
            if (!explored) {
                explored = true;
            }
            actors.add(actor);
            return true;
        }

        if (actor instanceof Enemy) {
            if (!containsEnemy()) {
                actors.add(actor);
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean exitCell(Actor actor) {
        if (actors.contains(actor)) {
            actors.remove(actor);
            return true;
        }
        return false;
    }
}
