package org.example;

import java.awt.Point;
import java.util.*;

public class MapGenerator {

    public GridMap generateMap(int difficulty) {
        Map<Point, Cell> cells = new HashMap<>();

        int width = 3 + difficulty * 2;
        int height = 3 + difficulty * 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                cells.put(new Point(x, y), new Cell("Default", new Point(x, y)));
            }
        }

        Point bookPosition = placeBookInRandomCell(cells, width, height);
        List<Point> path = findShortestPath(cells, new Point(0, 0), bookPosition);

        List<Point> pathCopy = new ArrayList<>(path);
        while (true) {
            boolean removed = false;
            for (Point p : pathCopy) {
                if (!p.equals(bookPosition) && !p.equals(new Point(0, 0))) {
                    Cell removedCell = cells.remove(p);
                    if (removedCell != null) {
                        List<Point> newPath = findShortestPath(cells, new Point(0, 0), bookPosition);
                        if (newPath.isEmpty()) {
                            cells.put(p, removedCell);
                        } else {
                            path = newPath;
                            removed = true;
                        }
                    }
                    break;
                }
            }
            if (!removed) break;
        }

        int totalCells = cells.size();
        int removeCount = totalCells / 4;
        Set<Point> pathSet = new HashSet<>(path);
        List<Point> keys = new ArrayList<>(cells.keySet());
        Collections.shuffle(keys);
        int removedRandom = 0;
        for (Point p : keys) {
            if (!pathSet.contains(p)) {
                cells.remove(p);
                removedRandom++;
            }
            if (removedRandom >= removeCount) break;
        }

        return new GridMap(cells, width, height);
    }

    private Point placeBookInRandomCell(Map<Point, Cell> map, int width, int height) {
        int x = RandomNumberGenerator.nextInt(width / 2, width - 1);
        int y = RandomNumberGenerator.nextInt(height / 2, height - 1);
        Point bookPos = new Point(x, y);
        Cell cell = map.get(bookPos);
        if (cell != null) {
            cell.placeBook();
        }
        return bookPos;
    }

    private List<Point> findShortestPath(Map<Point, Cell> map, Point start, Point goal) {
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Set<Point> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);

        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            if (current.equals(goal)) break;
            for (int[] d : directions) {
                Point next = new Point(current.x + d[0], current.y + d[1]);
                if (map.containsKey(next) && !visited.contains(next)) {
                    queue.add(next);
                    visited.add(next);
                    cameFrom.put(next, current);
                }
            }
        }

        List<Point> path = new ArrayList<>();
        if (!cameFrom.containsKey(goal) && !start.equals(goal)) return path;
        Point current = goal;
        path.add(current);
        while (!current.equals(start)) {
            current = cameFrom.get(current);
            path.add(0, current);
        }
        return path;
    }
}
