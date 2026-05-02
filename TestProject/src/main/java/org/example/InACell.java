package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InACell extends State {

    private final Direction[] DIRECTIONS = {
            new Direction(new Point(0, -1)), // North
            new Direction(new Point(1, 0)),  // East
            new Direction(new Point(0, 1)),  // South
            new Direction(new Point(-1, 0))  // West
    };

    private Player player;
    private List<String> currentChoices;
    private List<GridMap> maps;
    private GridMap currentMap;

    public InACell(InputReader inputReader, StateMachine stateMachine) {
        super(inputReader, stateMachine);
        player = stateMachine.getPlayer();

        MapGenerator mapGenerator = new MapGenerator();
        maps = new LinkedList<>();

        choices.add("Anfall Demon");
        choices.add("Inspektera Rummet");
        choices.add("Ro NORR");
        choices.add("Ro ÖST");
        choices.add("Ro SYD");
        choices.add("Ro VÄST");
        choices.add("Ta Tunnelbanan Hem");

        for (int n = 1; n <= 8; n++) {
            maps.add(mapGenerator.generateMap(n));
        }
    }

    public void setCurrentMap(GridMap map) {
        this.currentMap = map;
    }

    @Override
    protected List<String> getChoices(List<String> availableChoices) {
        if (currentMap == null) {
            currentMap = maps.get(stateMachine.getCurrentArea().getDifficulty());
            currentMap.moveActorToOrigin(player);
        }

        List<String> newChoices = new ArrayList<>();

        if (player.getCell().containsEnemy()) {
            newChoices.add(choices.getFirst());
        }

        newChoices.add(choices.get(1));

        List<Direction> availableDirections = currentMap.getAvailableDirections(player);

        for (Direction direction : DIRECTIONS) {
            for (Direction available : availableDirections) {
                if (direction.getName().equals(available.getName())) {
                    switch (direction.getName()) {
                        case "North" -> newChoices.add(choices.get(2));
                        case "East" -> newChoices.add(choices.get(3));
                        case "South" -> newChoices.add(choices.get(4));
                        case "West" -> newChoices.add(choices.get(5));
                    }
                }
            }
        }

        newChoices.add(choices.get(6));

        currentChoices = newChoices;
        return currentChoices;
    }

    @Override
    protected StateMachine.States getNextState(int result) {



        String choice = currentChoices.get(result-1);
        switch (choice) {
            case "Anfall Demon":
                System.out.println("Attackerade Demon");
                break;
            case "Inspektera Rummet":
                System.out.println("Du hittade ingenting i Rummet");
                break;
            case "Ro NORR":
                currentMap.moveActorInDirection(player, DIRECTIONS[0]);

                break;
            case "Ro ÖST":
                currentMap.moveActorInDirection(player, DIRECTIONS[1]);
                break;
            case "Ro SYD":
                currentMap.moveActorInDirection(player, DIRECTIONS[2]);
                break;
            case "Ro VÄST":
                currentMap.moveActorInDirection(player, DIRECTIONS[3]);
                break;
            case "Ta Tunnelbanan Hem":
                System.out.println("Du tog tunnelbanan hem.");
                return StateMachine.States.HUBWORLD;
            default:
                System.out.println("Ogiltigt val");
        }
        return StateMachine.States.IN_A_CELL;
    }
}
