package org.example;

import java.util.List;

public class HubWorld extends State {

    public HubWorld(InputReader inputReader, StateMachine stateMachine) {
        super(inputReader, stateMachine);
        choices.add("Ta tentamen");
        choices.add("Utforska avloppet");
    }

    @Override
    protected List<String> getChoices(List<String> availableChoices) {
        return choices;
    }

    @Override
    protected StateMachine.States getNextState(int result) {
        return switch (result) {
            case 1 -> StateMachine.States.TAKING_EXAM;
            case 2 -> StateMachine.States.PICKING_AREA;
            default -> StateMachine.States.HUBWORLD;
        };
    }
}
