package test.java;

import static org.junit.jupiter.api.Assertions.*;
import org.example.*;
import org.junit.jupiter.api.Test;

import java.io.*;




public class TakingExamTest {


    @Test
    void questionRemovedAfterOverwrite() {
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        TakingExam.LinkedList[] table = exam.getTable();
        Question newQuestion = new Question("What is the biggest building?", 50);
        table[0].addData(newQuestion);

        TakingExam newExam = new TakingExam(new InputReader(), new StateMachine());

        String questionRemoved = null;
        assertEquals(questionRemoved,table[0].getQuestion(49));
    }

    @Test
    void removeRetainsDeletedQuestion() {
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        TakingExam.LinkedList[] table = exam.getTable();
        Question question = new Question("How big is the Big Ben ", 25);
        table[0].addData(question);
        table[0].remove(question);

        boolean tableContainsQuestion = true;
        assertEquals(tableContainsQuestion, table[0].contains(question));
    }

    @Test
    void addDataThrowsIllegalArgumentIfQuestionWordingIsNull() {
        TakingExam exam = new TakingExam(new InputReader(), new StateMachine());
        TakingExam.LinkedList[] table = exam.getTable();

        Question newQuestion = new Question(null, 40);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            table[0].addData(newQuestion);
        });
    }
}
