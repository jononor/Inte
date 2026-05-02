package org.example;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GridMap {
    private final Map<Point, Cell> cells;
    private final Map<Point, Boolean> displayGrid;

    private final Direction[] DIRECTIONS = {
            new Direction(new Point(0, -1)), // North
            new Direction(new Point(0, 1)),  // South
            new Direction(new Point(-1, 0)), // West
            new Direction(new Point(1, 0))   // East
    };

    public GridMap(Map<Point, Cell> cells, int width, int height) {
        this.cells = cells;
        this.displayGrid = new LinkedHashMap<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Point p = new Point(x, y);
                boolean explored = (p.equals(new Point(0, 0)) ||
                        p.equals(new Point(0, 1)) ||
                        p.equals(new Point(1, 0)));
                displayGrid.put(p, explored);
            }
        }
    }

    public ArrayList<Direction> getAvailableDirections(Cell cell) {
        ArrayList<Direction> available = new ArrayList<>();
        Point pos = cell.getPosition();

        for (Direction d : DIRECTIONS) {
            Point neighbor = new Point(pos.x + d.getPoint().x, pos.y + d.getPoint().y);
            if (cells.containsKey(neighbor)) available.add(d);
        }

        return available;
    }

    public void moveActorToOrigin(Actor actor) {
        Point origin = new Point(0, 0);
        Cell targetCell = cells.get(origin);
        actor.moveTo(targetCell);
        if (actor instanceof Player) {
            revealAdjacentCells(origin);
        }
    }


    public ArrayList<Direction> getAvailableDirections(Actor actor) {
        return getAvailableDirections(actor.getCell());
    }

    public void moveActorInDirection(Actor actor, Direction direction) {
        Point position = actor.getCell().getPosition();
        Point targetPos = new Point(position.x + direction.getPoint().x, position.y + direction.getPoint().y);
        Cell cell = cells.get(targetPos);

        if (cell == null) {
            throw new IllegalArgumentException("No cell exists at target position: " + targetPos);
        }

        actor.moveTo(cell);

        if (actor instanceof Player) {
            revealAdjacentCells(targetPos);
        }
    }

    private void revealAdjacentCells(Point center) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                Point neighbor = new Point(center.x + dx, center.y + dy);
                if (displayGrid.containsKey(neighbor)) {
                    displayGrid.put(neighbor, true);
                }
            }
        }
    }

    public Point getActorPosition(Actor actor) {
        return actor.getCell().getPosition();
    }

    @Override
    public String toString() {
        if (displayGrid.isEmpty()) return "[Empty Map]";

        int maxX = 0;
        int maxY = 0;
        for (Point p : displayGrid.keySet()) {
            if (p.x > maxX) maxX = p.x;
            if (p.y > maxY) maxY = p.y;
        }

        StringBuilder sb = new StringBuilder();
        int cellWidth = 3;

        sb.append("   ");
        for (int x = 0; x <= maxX; x++) {
            sb.append(String.format("%" + cellWidth + "d", x));
        }
        sb.append("\n");

        for (int y = 0; y <= maxY; y++) {
            sb.append(String.format("%2d ", y));
            for (int x = 0; x <= maxX; x++) {
                Point p = new Point(x, y);
                boolean explored = displayGrid.get(p);

                String symbol;
                if (!cells.containsKey(p)) {
                    symbol = (explored) ? "X" : "?";
                } else if (!explored) {
                    symbol = "?";
                } else {
                    symbol = "_";
                }

                sb.append(String.format("%" + cellWidth + "s", symbol));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Point getBookPosition() {
        for (Map.Entry<Point, Cell> entry : cells.entrySet()) {
            if (entry.getValue().containsBook()) {
                return entry.getKey();
            }
        }
        return null;
    }

    public String toStringFullyExplored() {
        if (displayGrid.isEmpty()) return "[Empty Map]";

        int maxX = 0;
        int maxY = 0;
        for (Point p : displayGrid.keySet()) {
            if (p.x > maxX) maxX = p.x;
            if (p.y > maxY) maxY = p.y;
        }

        Point bookPos = getBookPosition();
        StringBuilder sb = new StringBuilder();
        int cellWidth = 3;

        sb.append("   ");
        for (int x = 0; x <= maxX; x++) {
            sb.append(String.format("%" + cellWidth + "d", x));
        }
        sb.append("\n");

        for (int y = 0; y <= maxY; y++) {
            sb.append(String.format("%2d ", y));
            for (int x = 0; x <= maxX; x++) {
                Point p = new Point(x, y);
                String symbol;
                if (cells.containsKey(p)) {
                    if (p.equals(bookPos)) {
                        symbol = "B";
                    } else {
                        symbol = "_";
                    }
                } else {
                    symbol = "X";
                }
                sb.append(String.format("%" + cellWidth + "s", symbol));
            }
            sb.append("\n");
        }

        return sb.toString();
    }


}
