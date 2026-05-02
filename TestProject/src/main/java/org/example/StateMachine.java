package org.example;

import java.util.HashMap;
import java.util.Map;

public class StateMachine {
    public enum States {
        HUBWORLD,
        TAKING_EXAM,
        COMPLETING_GAME,
        PICKING_AREA,
        IN_A_CELL
    }

    private int books;
    private final Player player;
    private Area currentArea = new Area(0, "name");
    private States currentState;
    private Map<States, State> states = new HashMap<>();
    private final InputReader inputReader;

    public StateMachine(InputReader inputReader) {
        this.inputReader = inputReader;
        this.player = new Player("Martinus Maximus");
        this.books = 3;
    }

    public StateMachine() {
        this.inputReader = new InputReader();
        this.player = new Player("Martinus Maximus");
        this.books = 3;

        states.put(States.HUBWORLD, new HubWorld(this.inputReader, this));
        states.put(States.TAKING_EXAM, new TakingExam(this.inputReader, this));
        states.put(States.COMPLETING_GAME, new CompletingGame(this.inputReader, this));
        states.put(States.PICKING_AREA, new PickingArea(this.inputReader, this));
        states.put(States.IN_A_CELL, new InACell(this.inputReader, this));


    }

    public void setAllStates(Map<States, State> states) {
        this.states = states;
    }

    public void changeState(States newState) {
        System.out.println(newState);
        currentState = newState;
        states.get(currentState).enterState();
    }

    public Player getPlayer() {
        return player;
    }

    public Area getCurrentArea() {
        return currentArea;
    }

    public void setCurrentArea(Area newArea) {
        currentArea = newArea;
    }
}
