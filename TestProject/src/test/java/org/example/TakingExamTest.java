package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.example.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TakingExamTest {
    /**
     * spelarn ska kunna ta examen direkt om den vill.
     * Spelaren börjar med 3 böcker. Anledningen att numret är 4, 6, 11 är att valet "Leave exam" ska alltid vara möjligt,
     */
    private static final int PLAYER_START_BOOKS_AND_LEAVE_EXAM = 4;
    private static final int HALF_OF_TOTAL_BOOKS_FOUND_AND_LEAVE_EXAM = 6;
    private static final int ALL_POSSIBLE_BOOKS_FOUND_AND_LEAVE_EXAM = 11;

    /**
     * Variablerna används för att kolla om TakingExams iterering stämmer.
     */
    private static final int HALF_OF_TOTAL_QUESTIONS_ANSWERED = 5;
    private static final int EXAM_COMPLETED = 9;

    private TakingExam exam;
    private List<String> choices;

    @BeforeEach
    void storeStandardSystemIn() {
        exam = new TakingExam(new InputReader(), new StateMachine());
    }


    @Test
    void questionRemovedAfterOverwrite() {
        TakingExam.LinkedList[] table = exam.getTable();
        Question newQuestion = new Question("What is the biggest building?", 50);
        table[0].addData(newQuestion);

        TakingExam newExam = new TakingExam(new InputReader(), new StateMachine());

        String questionRemoved = null;
        assertEquals(questionRemoved,table[0].getQuestion(49));
    }

    @Test
    void removeQuestion() {
        TakingExam.LinkedList[] table = exam.getTable();
        Question question = new Question("How big is the Big Ben ", 25);
        table[0].addData(question);
        table[0].remove(question);

        boolean tableContainsQuestion = false;
        assertEquals(tableContainsQuestion, table[0].contains(question));
    }

    @Test
    void addDataThrowsIllegalArgumentIfQuestionWordingIsNull() {
        TakingExam.LinkedList[] table = exam.getTable();

        Question newQuestion = new Question(null, 40);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            table[0].addData(newQuestion);
        });
    }

    @Test
    void addDataThrowsIllegalArgumentIfQuestionWordingIsStringLengthZero() {
        TakingExam.LinkedList[] table = exam.getTable();

        Question newQuestion = new Question("  ", 20);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            table[0].addData(newQuestion);
        });
    }

    @Test
    void removeLastQuestion() {
        TakingExam.LinkedList[] table = exam.getTable();
        Question questionLongFinger = new Question("What is the longest finger", 25);
        Question questionSmallestCountry = new Question("What is the smallest country", 67);

        table[0].addData(questionLongFinger);
        table[0].addData(questionSmallestCountry);

        table[0].remove(questionSmallestCountry);
        table[0].remove(questionSmallestCountry);

        assertTrue(table[0].contains(questionLongFinger));

        assertFalse(table[0].contains(questionSmallestCountry));
    }

    @Test
    void getMethodsReturnsCorrectAnswer() {
        TakingExam.LinkedList.Node node = new TakingExam.LinkedList.Node(new Question("What is the longest Animal", 5));
        int correctAnswer = 5;

        assertEquals(5, node.getCorrectAnswer());
    }

    @Test
    void nodeEqualsTheSameNode() {
        TakingExam.LinkedList.Node node = new TakingExam.LinkedList.Node(new Question("What city is the largest", 4));

        assertTrue(node.equals(node));
    }

    @Test
    void nodeNotEqualsNull() {
        TakingExam.LinkedList.Node node = new TakingExam.LinkedList.Node(new Question("How big is the brain", 11));

        assertFalse(node.equals(null));
    }

    @Test
    void nodeNotEqualsOtherObjects() {
        TakingExam.LinkedList.Node node = new TakingExam.LinkedList.Node(new Question("What city is the fish", 4));

        assertFalse(node.equals("String"));
    }


    @Test
    void nodesWithSameQuestionEquals() {
        //Noder med samma fråga
        TakingExam.LinkedList.Node nodeOne = new TakingExam.LinkedList.Node(new Question("Can you lick your elbow", 10));
        TakingExam.LinkedList.Node nodeTwo = new TakingExam.LinkedList.Node(new Question("Can you lick your elbow", 10));

        //Nod med en annordlunda fråga
        TakingExam.LinkedList.Node nodeThree = new TakingExam.LinkedList.Node(new Question("What is the height of Eiffel Tower", 5));
        TakingExam.LinkedList.Node nodeFour = new TakingExam.LinkedList.Node(new Question("Can you lick your elbow", 50));

        assertTrue(nodeOne.equals(nodeTwo));

        assertFalse(nodeOne.equals(nodeThree));
        assertFalse(nodeOne.equals(nodeFour));
    }

    @Test
    void withOnlyThe_StartingBooksFound_TestGetNextState_AndAnswersQuestionCorrectly() {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        exam.setCollectedBooksAndLeaveExam(PLAYER_START_BOOKS_AND_LEAVE_EXAM);
        choices = exam.getChoices(new ArrayList<>());


        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);
        assertEquals(StateMachine.States.TAKING_EXAM, nextState);
        assertEquals(1, exam.getQuestionsAnswered());
    }

    @Test
    void withOnlyHalf_Of_Total_Books_Found_TestGetNextState_AndAnswersWrong() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        exam.setCollectedBooksAndLeaveExam(HALF_OF_TOTAL_BOOKS_FOUND_AND_LEAVE_EXAM);
        while(exam.getQuestionsAnswered() < HALF_OF_TOTAL_QUESTIONS_ANSWERED) {
            exam.increaseQuestionsAnswered();
        }

        choices = exam.getChoices(new ArrayList<>());


        int PlayersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(PlayersChoice);
        assertEquals(StateMachine.States.TAKING_EXAM, nextState);
        assertEquals(5, exam.getQuestionsAnswered());
    }

    @Test
    void withOnlyThe_StartingBooksFound_TestGetNextState_LeavesExam_ToHubWorld() {
        String input = "1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));


        exam.setCollectedBooksAndLeaveExam(PLAYER_START_BOOKS_AND_LEAVE_EXAM);
        choices = exam.getChoices(new ArrayList<>());


        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);
        assertEquals(StateMachine.States.HUBWORLD, nextState);
    }

    @Test
    void withAll_Of_Total_Books_TestGetNextState_AndFinishExam() {
        String input = "10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        exam.setCollectedBooksAndLeaveExam(ALL_POSSIBLE_BOOKS_FOUND_AND_LEAVE_EXAM);
        while(exam.getQuestionsAnswered() < EXAM_COMPLETED) {
            exam.increaseQuestionsAnswered();
        }

        choices = exam.getChoices(new ArrayList<>());

        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);

        assertEquals(StateMachine.States.COMPLETING_GAME, nextState);
        assertEquals(EXAM_COMPLETED, exam.getQuestionsAnswered());
    }
}
