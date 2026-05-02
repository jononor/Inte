package org.example;

import java.awt.Point;

public class Direction {
    private final String name;
    private final Point point;

    public Direction(Point point) {
        if (point.x == 0 && point.y == -1) {
            name = "North";
        } else if (point.x == 0 && point.y == 1) {
            name = "South";
        } else if (point.x == -1 && point.y == 0) {
            name = "West";
        } else if (point.x == 1 && point.y == 0) {
            name = "East";
        } else {
            throw new IllegalArgumentException(
                    "Invalid direction point: must be one of (0,-1), (0,1), (-1,0), (1,0)"
            );
        }
        this.point = point;
    }

    public Point getPoint() {
        return point;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direction)) return false;
        Direction other = (Direction) o;
        return this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
