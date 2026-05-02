package org.example;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public abstract class State {
    //bör vara privata och skrivna i VERSALER
    final List<String> choices;
    final InputReader inputReader;
    final StateMachine stateMachine;

    public State(InputReader inputReader, StateMachine stateMachine) {
        //nullcheck?
        this.inputReader = inputReader;
        this.stateMachine = stateMachine;
        this.choices = new ArrayList<>(); //flytta till instansvariabler?
    }

    //bör heta "enter", den tar ingen parameter och känns som enterState ska ta ett state som parameter.
    public void enterState() {
        List<String> availableChoices = getChoices(new ArrayList<>());
        //displayChoices(availableChoices);
        int result = makeChoice(availableChoices);
        if (result == -1){
            throw new InputMismatchException();
        }
        StateMachine.States nextState = getNextState(result);
        stateMachine.changeState(nextState);
    }

    private void displayChoices(List<String> choices) {
        //kontroll att listan inte är empty/null, kasta undantag skulle behövas
        for (int i = 0; i < choices.size(); i++) {
            System.out.println((i + 1) + ". " + choices.get(i));
        }
    }

    //namngivning och kommentar saknas - förslagsvis javaDocs kommentar, aningen otydlig.
    //Förslag: getResultFromChoices
    private int makeChoice(List<String> choices) {
        return inputReader.getUserChoice(choices);
    }

    protected abstract List<String> getChoices(List<String> availableChoices);

    //namngivning?
    //Förslag: getChosenState
    protected abstract StateMachine.States getNextState(int result);

    //Avsaknaden av kommentarer vid olika tillfällen
}
