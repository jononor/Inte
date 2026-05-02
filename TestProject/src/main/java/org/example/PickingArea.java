package org.example;

import java.util.ArrayList;
import java.util.List;

public class PickingArea extends State {

    private final List<Area> areas;

    public PickingArea(InputReader inputReader, StateMachine stateMachine) {
        super(inputReader, stateMachine);
        areas = new ArrayList<>();

        areas.add(new Area(1, "Stockholm"));
        areas.add(new Area(2, "Solna"));
        areas.add(new Area(3, "Sundbyberg"));
        areas.add(new Area(4, "Nacka"));
        areas.add(new Area(5, "Täby"));
        areas.add(new Area(6, "Sollentuna"));
        areas.add(new Area(7, "Haninge"));
        areas.add(new Area(8, "Huddinge"));

        choices.add("Gå tillbaka till hubvärld");

        for (Area area : areas) {
            choices.add(area.getName());
        }


    }

    @Override
    protected List<String> getChoices(List<String> availableChoices) {
        return choices;
    }

    @Override
    protected StateMachine.States getNextState(int result) {
        if (result == 1) {
            return StateMachine.States.HUBWORLD;
        }
        else if (result < 8) {
            stateMachine.setCurrentArea(areas.get(result));
            return StateMachine.States.IN_A_CELL;
        }
        else {
            return StateMachine.States.HUBWORLD;
        }
    }
}
