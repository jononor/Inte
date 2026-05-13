package org.example;

public class Question {

    private final String questionWording;
    private final int correctAnswer;

    public Question(String questionWording, int correctAnswer) {
        this.questionWording = questionWording;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionWording() {
        return questionWording;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


}
