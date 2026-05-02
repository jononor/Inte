package org.example;

public class Question {

    private final String question;
    private final int correctAnswer;

    public Question(String question, int correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


}
