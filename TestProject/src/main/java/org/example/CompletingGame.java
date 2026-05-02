package org.example;

import java.util.ArrayList;
import java.util.List;

public class CompletingGame extends State{




    public CompletingGame(InputReader inputReader,StateMachine stateMachine) {
        super(inputReader, stateMachine);
        choices.add("Close Game");
        choices.add("Restart Game");
    }

    @Override
    public List<String> getChoices(List<String> availableChoices) {
        System.out.println("Completed Game");
        availableChoices.addAll(choices);
        return availableChoices;
    }

    @Override
    public StateMachine.States getNextState(int result) {
        StateMachine.States nextState;
        result = result - 1;
        if(result == 0) {
            System.out.println("Congratulations! You have completed the exam!");
            nextState = StateMachine.States.COMPLETING_GAME;


        } else {
            System.out.println("Restart Game...");
            nextState = StateMachine.States.HUBWORLD;
        }


        return nextState;
    }
}
