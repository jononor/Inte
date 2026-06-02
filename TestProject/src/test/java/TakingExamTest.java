package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.example.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.print.Book;
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
    void removeRetainsDeletedQuestion() {
        TakingExam.LinkedList[] table = exam.getTable();
        Question question = new Question("How big is the Big Ben ", 25);
        table[0].addData(question);
        table[0].remove(question);

        boolean tableContainsQuestion = true;
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
    void withOnlyThe_StartingBooksFound_TestGetNextState_AndAnswersQuestionCorrectly() {
        String input = "2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        exam.setCollectedBooksAndLeaveExam(PLAYER_START_BOOKS_AND_LEAVE_EXAM);
        choices = exam.getChoices(new ArrayList<>());
        for (int printChoice = 0; printChoice < choices.size(); printChoice++) {
            System.out.println((printChoice + 1) + ". " + choices.get(printChoice));
        }

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
        for (int printChoice = 0; printChoice < choices.size(); printChoice++) {
            System.out.println((printChoice + 1) + ". " + choices.get(printChoice));
        }

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
        /*
        for (int printChoices = 0; printChoices < choices.size(); printChoices++) {
            System.out.println((printChoices + 1) + ". " + choices.get(printChoices));
        }

         */

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
