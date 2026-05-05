package org.example;

import java.util.*;

public class TakingExam extends State{

    private static final int QUESTIONS_TO_BEAT_GAME = 9;
    private int questionsAnswered = 0;

    /**
     * ska inte vara i klassen. Är igentligen en global variabel. ÄR bara här för tydliggöra hur getChoices metoden använder courseBook
     * courseBook Ska alltid vara minimun fyra. Leave exam valet + de 3 start böckerna
     */
    private int courseBooksAndLeaveExam;

    public List<Question> questions = new ArrayList<>();


    public TakingExam(InputReader inputReader, StateMachine stateMachine) {
        super(inputReader, stateMachine);

        table = new LinkedList[HASH_SIZE];
        for (int index = 0; index < table.length; index++) {
            table[index] = new LinkedList();
        }

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
        fillQuestionList();
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

    public void increaseQuestionsAnswered() {
        questionsAnswered++;
    }


    public List<String> getChoices(List<String> playerOptions) {

        Question currentQuestion = questions.get(questionsAnswered);
        System.out.println(currentQuestion.getQuestion());

        for (int foundCourseBooks = 0; foundCourseBooks < courseBooksAndLeaveExam; foundCourseBooks++) {
            playerOptions.add(choices.get(foundCourseBooks));
        }

        return playerOptions;
    }


    public StateMachine.States getNextState(int result) {
        StateMachine.States nextState;
        result = result - 1;
        Question currentQuestion = questions.get(questionsAnswered);
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


    private void fillQuestionList() {
        Question question1 = new Question("First question: What is a Integer", 1);
        questions.add(question1);
        Question question2 = new Question("Third question: Second question: What is a float", 2);
        questions.add(question2);
        Question question3 = new Question("Forth question: What is a beaver", 3);
        questions.add(question3);
        Question question4 = new Question("Fifth question: What is a largest mountain", 4);
        questions.add(question4);
        Question question5 = new Question("Sixth question: What is the tiniest animal", 5);
        questions.add(question5);
        Question question6 = new Question("Seventh question: What is the biggest building", 6);
        questions.add(question6);
        Question question7 = new Question("Eight question: What is the hardest language", 7);
        questions.add(question7);
        Question question8 = new Question("Ninth question: How do you declare a array", 8);
        questions.add(question8);
        Question question9 = new Question("Tenth What is the most popular language", 9);
        questions.add(question9);
        Question question10 = new Question("Last question: What is the biggest city language", 10);
        questions.add(question10);
    }


    private static final int PRIM_NUMBER_FIVE = 5;
    private static final int PRIM_NUMBER_THREE = 3;
    private static final int NEGATIVE_ONE = -1;
    private static final int HASH_SIZE = 100;
    private static LinkedList[] table;


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


    public static class LinkedList {
        private static int count = 0;
        private Node head;
        private int size;

        public static class Node {
            private int id;
            private String data;
            private LinkedList list;
            private Node next;

            public Node(String data) {
                this.data = data;
                this.list = new LinkedList();
                this.id = count++;
            }

            public String getData() {
                return data;
            }
            public int getId() {
                return id;
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
                if (this.id == other.id && this.data == other.data) {
                    return true;
                }
                return false;
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

        public void addData(String newData) {
            isDataIsIllegalArgument(newData);
            int index = getHashValue(newData);
            if (index < 0) {
                index = toPositive(index);
            }
            Node newNode = new Node(newData);
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

        public boolean contains(String data) {
            isDataIsIllegalArgument(data);
            int index = getHashValue(data);
            if (index < 0 ) {
                index = toPositive(index);
            }
            Node current = table[index].head;
            while (current.next != null) {
                if (current.data.equals(data)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }

        public void remove(String data) {
            isDataIsIllegalArgument(data);
            int index = getHashValue(data);
            if (index < 0 ) {
                index = toPositive(index);
            }
            Node current = table[index].head;
            Node prev = current;
            while (current.next != null) {
                if (current.data.equals(data)) {
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

        private void isDataIsIllegalArgument(String data) {
            if (data == null || data.trim().isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

        private int getHashValue(String data) {
            int HashValue = 1;
            for (int index = 0; index < data.length(); index++) {
                char holder = data.charAt(index);
                HashValue *= data.hashCode() + holder;
            }
            return HashValue % HASH_SIZE;
        }

        private int toPositive(int num) {
            return num * NEGATIVE_ONE;
        }



    }
}
