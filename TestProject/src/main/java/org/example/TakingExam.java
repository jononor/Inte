package org.example;

import java.util.*;

public class TakingExam extends State{
    private static final int QUESTIONS_TO_BEAT_GAME = 9;
    private static final int PRIM_NUMBER_FIVE = 5;
    private static final int PRIM_NUMBER_THREE = 3;
    private static final int NEGATIVE_ONE = -1;
    private static final int HASH_SIZE = 100;
    private static LinkedList[] table;
    private int questionsAnswered = 0;

    /**
     * ska inte vara i klassen. Är igentligen en global variabel. ÄR bara här för tydliggöra hur getChoices metoden använder courseBook
     * courseBook Ska alltid vara minimun fyra. Leave exam valet + de 3 start böckerna
     */
    private int courseBooksAndLeaveExam;

    public TakingExam(InputReader inputReader, StateMachine stateMachine) {
        super(inputReader, stateMachine);

        table = new LinkedList[HASH_SIZE];
        for (int index = 0; index < table.length; index++) {
            table[index] = new LinkedList();
        }
        fillQuestionList();

        choices.add("Leave exam");
        choices.add("CourseBook 1");
        choices.add("CourseBook 2");
        choices.add("CourseBook 3");
        choices.add("CourseBook 4");
        choices.add("CourseBook 5");
        choices.add("CourseBook 6");
        choices.add("CourseBook 7");
        choices.add("CourseBook 8");
        choices.add("CourseBook 9");
        choices.add("CourseBook 10");
    }

    public static class LinkedList {
        private static int count = 0;
        private Node head;
        private int size;

        public static class Node {
            private int id;
            private Question question;
            private LinkedList list;
            private Node next;

            public Node(Question question) {
                this.question = question;
                this.list = new LinkedList();
                this.id = count++;
            }

            public String getQuestionWording() {
                return question.getQuestionWording();
            }
            public int getId() {
                return id;
            }
            public Question getQuestion() {
                return question;
            }
            public int getCorrectAnswer() {
                return question.getCorrectAnswer();
            }
            public LinkedList getList() {
                return list;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null) {
                    return false;
                }
                Node other = (Node) obj;
                boolean sameQuestion = hasSameQuestionWording(other) && hasSameAnswer(other);
                if (sameQuestion && hasSameId(other)) {
                    return true;
                }
                return false;
            }
            private boolean hasSameQuestionWording(Node other) {
                return this.question.getQuestionWording().equals(other.question.getQuestionWording());
            }
            private boolean hasSameAnswer(Node other) {
                return this.getCorrectAnswer() == other.getCorrectAnswer();
            }
            private boolean hasSameId(Node other) {
                return this.id == other.id;
            }

        }

        public static int getCount() {
            return count;
        }
        public int getSize() {
            return size;
        }
        public void increaseListSize() {
            size++;
        }

        public void addData(Question newQuestion) {
            isQuestionWordingIllegalArgument(newQuestion.getQuestionWording());
            int index = getHashValue(newQuestion.getQuestionWording());
            if (index < 0) {
                index = toPositive(index);
            }
            Node newNode = new Node(newQuestion);
            table[index].addNode(newNode);
        }

        public void addNode(Node node) {
            if (head == null) {
                head = node;
                increaseListSize();
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = node;
                increaseListSize();
            }
        }

        public boolean contains(Question question) {
            isQuestionWordingIllegalArgument(question.getQuestionWording());
            int index = getHashValue(question.getQuestionWording());
            if (index < 0 ) {
                index = toPositive(index);
            }
            Node current = table[index].head;
            while (current != null) {
                if (current.getQuestion().equals(question)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public void remove(Question question) {
            isQuestionWordingIllegalArgument(question.getQuestionWording());
            int index = getHashValue(question.getQuestionWording());
            if (index < 0 ) {
                index = toPositive(index);
            }
            Node current = table[index].head;
            Node prev = current;
            while (current != null) {
                if (current.getQuestion().equals(question)) {
                    if(current == head) {
                        head = current.next;
                    }
                    prev.next = current.next;
                    current.next = null;
                    return;
                }
                prev = current;
                current = current.next;
            }
        }

        public Question getQuestion(int questionsAnswered) {
            Question question = null;
            for(int index = 0; index < table.length; index++) {
                Node current = table[index].head;
                while(current != null) {
                    System.out.println("Node id: " + current.getId());
                    if(current.getQuestion().getCorrectAnswer() == questionsAnswered + 1) {
                        question = current.getQuestion();
                    }
                    current = current.next;
                }
            }
            return question;
        }

        private void isQuestionWordingIllegalArgument(String questionWording) {
            if (questionWording == null || questionWording.trim().isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

        private int getHashValue(String questionWording) {
            int HashValue = 1;
            for (int index = 0; index < questionWording.length(); index++) {
                char holder = questionWording.charAt(index);
                HashValue *= questionWording.hashCode() + holder;
            }
            return HashValue % HASH_SIZE;
        }

        private int toPositive(int num) {
            return num * NEGATIVE_ONE;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TakingExam other = (TakingExam) obj;
        for (int index = 0; index < table.length; index++) {
            if (!table[index].equals(other.table[index])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return PRIM_NUMBER_FIVE * PRIM_NUMBER_THREE;
    }

    public void increaseQuestionsAnswered() {
        questionsAnswered++;
    }

    public List<String> getChoices(List<String> playerOptions) {
        Question currentQuestion = table[0].getQuestion(questionsAnswered);
        System.out.println(currentQuestion.getQuestionWording());

        for (int foundCourseBooks = 0; foundCourseBooks < courseBooksAndLeaveExam; foundCourseBooks++) {
            playerOptions.add(choices.get(foundCourseBooks));
        }
        return playerOptions;
    }

    public StateMachine.States getNextState(int result) {
        StateMachine.States nextState;
        result = result - 1;
        Question currentQuestion = table[0].getQuestion(questionsAnswered);
        //Question currentQuestion = questions.get(questionsAnswered);
        if(questionsAnswered == QUESTIONS_TO_BEAT_GAME) {
            System.out.println("Congratulations! You have completed the exam!");
            nextState = StateMachine.States.valueOf("COMPLETING_GAME");

        } else if (result  == currentQuestion.getCorrectAnswer()) {
            System.out.println("Correct, next question");
            increaseQuestionsAnswered();
            nextState = StateMachine.States.valueOf("TAKING_EXAM");

        } else if (result == 0) {
            System.out.println("bye bye");
            nextState = StateMachine.States.valueOf("HUBWORLD");

        } else {
            System.out.println("Wrong answer!");
            nextState = StateMachine.States.valueOf("TAKING_EXAM");
        }
        return nextState;
    }

    /**
     * metoden har ingen betydelse utöver att testa klassen
     */
    public int setCollectedBooksAndLeaveExam(int number) {
        courseBooksAndLeaveExam = number;
        return courseBooksAndLeaveExam;
    }

    /**
     * metoden har ingen betydelse utöver att testa klassen
     */
    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    private void fillQuestionList() {
        Question question1 = new Question("First question: What is a Integer", 1);
        table[0].addData(question1);
        Question question2 = new Question("Third question: Second question: What is a float", 2);
        table[0].addData(question2);
        Question question3 = new Question("Forth question: What is a beaver", 3);
        table[0].addData(question3);
        Question question4 = new Question("Fifth question: What is a largest mountain", 4);
        table[0].addData(question4);
        Question question5 = new Question("Sixth question: What is the tiniest animal", 5);
        table[0].addData(question5);
        Question question6 = new Question("Seventh question: What is the biggest building", 6);
        table[0].addData(question6);
        Question question7 = new Question("Eight question: What is the hardest language", 7);
        table[0].addData(question7);
        Question question8 = new Question("Ninth question: How do you declare a array", 8);
        table[0].addData(question8);
        Question question9 = new Question("Tenth What is the most popular language", 9);
        table[0].addData(question9);
        Question question10 = new Question("Last question: What is the biggest city language", 10);
        table[0].addData(question10);
    }
}
