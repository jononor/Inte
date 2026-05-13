package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.example.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private List<StateMachine.States> statesList;

    private InputStream standardSystemIn;
    private PrintStream standardSystemOut;


    @BeforeEach
    void storeStandardSystemIn() {
        standardSystemIn = System.in;
        standardSystemOut = System.out;
    }


    @AfterEach
    void restoreStandardSystemIn() {
        System.setIn(standardSystemIn);
        System.setOut(standardSystemOut);
    }


    @Test
    void withOnlyThe_StartingBooksFound_TestGetNextState_AndAnswersQuestionCorrectly() {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        //Allt under görs i enterState() metoden som inte hann att implementeras
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        exam.setCollectedBooksAndLeaveExam(PLAYER_START_BOOKS_AND_LEAVE_EXAM);
        System.out.println("0 questions Correctly answered: " + exam.getQuestionsAnswered());
        List<String> choices = exam.getChoices(new ArrayList<>());
        for (int printChoice = 0; printChoice < choices.size(); printChoice++) {
            System.out.println((printChoice + 1) + ". " + choices.get(printChoice));
        }

        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);
        System.out.println("1 question correctly answered: " + exam.getQuestionsAnswered());
        assertEquals(StateMachine.States.TAKING_EXAM, nextState);
        assertEquals(1, exam.getQuestionsAnswered());
    }

    @Test
    void withOnlyHalf_Of_Total_Books_Found_TestGetNextState_AndAnswersWrong() {
        String input = "3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        exam.setCollectedBooksAndLeaveExam(HALF_OF_TOTAL_BOOKS_FOUND_AND_LEAVE_EXAM);
        while(exam.getQuestionsAnswered() < HALF_OF_TOTAL_QUESTIONS_ANSWERED) {
            exam.increaseQuestionsAnswered();
        }
        //Allt under görs i enterState() metoden som inte hann att implementeras
        List<String> choices = exam.getChoices(new ArrayList<>());
        for (int printChoice = 0; printChoice < choices.size(); printChoice++) {
            System.out.println((printChoice + 1) + ". " + choices.get(printChoice));
        }

        int PlayersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(PlayersChoice);
        assertEquals(StateMachine.States.TAKING_EXAM, nextState);
        assertEquals(5, exam.getQuestionsAnswered()); // Shouldn't increment
    }

    @Test
    void withOnlyThe_StartingBooksFound_TestGetNextState_LeavesExam_ToHubWorld() {
        String inout = "1\n";
        System.setIn(new ByteArrayInputStream(inout.getBytes()));

        //Allt under görs i enterState() metoden som inte hann att implementeras
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        exam.setCollectedBooksAndLeaveExam(PLAYER_START_BOOKS_AND_LEAVE_EXAM);
        System.out.println("0 questions Correctly answered: " + exam.getQuestionsAnswered());
        List<String> choices = exam.getChoices(new ArrayList<>());
        for (int printChoices = 0; printChoices < choices.size(); printChoices++) {
            System.out.println((printChoices + 1) + ". " + choices.get(printChoices));
        }

        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);
        assertEquals(StateMachine.States.HUBWORLD, nextState);
    }


    @Test
    void withAll_Of_Total_Books_TestGetNextState_AndFinishExam() {
        String input = "10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        exam.setCollectedBooksAndLeaveExam(ALL_POSSIBLE_BOOKS_FOUND_AND_LEAVE_EXAM);
        while(exam.getQuestionsAnswered() < EXAM_COMPLETED) {
            exam.increaseQuestionsAnswered();
        }
        //Allt under görs i enterState() metoden som inte hann att implementeras
        List<String> choices = exam.getChoices(new ArrayList<>());
        for (int printChoices = 0; printChoices < choices.size(); printChoices++) {
            System.out.println((printChoices + 1) + ". " + choices.get(printChoices));
        }
        int playersChoice = new InputReader().getUserChoice(choices);
        StateMachine.States nextState = exam.getNextState(playersChoice);

        assertEquals(StateMachine.States.COMPLETING_GAME, nextState);
        assertEquals(EXAM_COMPLETED, exam.getQuestionsAnswered()); // Shouldn't increment
    }

    @Test
    void checkGetHashValue() {
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        Question newQuestion = new Question("First question: What is a Integer", 1);
        TakingExam.LinkedList[] table = exam.getTable();
        table[0].addData(newQuestion);
    }
}
//testa buffer overflow på