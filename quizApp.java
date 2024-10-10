import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class quizApp {
    public static Scanner sc = new Scanner(System.in);
    public static volatile int a = -1;

    private static class Question {
        String quesText;
        String[] options;
        int ans;

        public Question(String quesText, String[] options, int ans) {
            this.quesText = quesText;
            this.options = options;
            this.ans = ans;
        }
    }

    private static ArrayList<Question> questions = new ArrayList<>();
    private static int score = 0;
    private static int currentQuestionIndex = 0;
    private static HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        questions.add(new Question("What is the capital city of India?",
                new String[] { "Lucknow", "Delhi", "Banglore", "Mumbai" }, 1));
        questions.add(new Question("What is the financial capital city of India?",
                new String[] { "Lucknow", "Delhi", "Banglore", "Mumbai" }, 3));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[] { "Mars", "Jupiter", "Venus", "Earth" }, 0));
        questions.add(new Question("How many ODI world cups have India won?",
                new String[] { "1", "2", "3", "4" }, 1));
        questions.add(new Question("Who is current T20I World Cup Champion",
                new String[] { "Australia", "New Zeelan", "India", "England" }, 2));
        System.out.println("\nGive answer before 10 seconds\n");
        while (currentQuestionIndex < questions.size()) {
            displayQues(currentQuestionIndex);
            currentQuestionIndex++;
        }
        displayRes();
    }

    private static void displayQues(int currentQuestionIndex) throws InterruptedException {
        Question q = questions.get(currentQuestionIndex);
        System.out.println("\nQ." + (currentQuestionIndex + 1) + ": " + q.quesText);
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + q.options[i]);
        }
        int ans = timeAndAnswer();
        map.put(currentQuestionIndex + 1, ans);
        if (ans == q.ans + 1) {
            score++;
        }
    }

    private static void displayRes() {
        System.out.println("\nYour final score is: " + score);
        System.out.println("Thanks for attending the quiz!!!!!!\n");
        System.out.println("Want to know the answers? (Y/N)\n");
        String ch;
        try {
            ch = sc.next();
        } catch (IndexOutOfBoundsException e) {
            ch = "N";
            sc.close();
        }
        if (ch.equalsIgnoreCase("y")) {
            for (int i = 0; i < 5; i++) {
                Question q = questions.get(i);
                System.out.println("\nQ." + (i + 1) + ". " + q.quesText);
                System.out.print("Answer is: \"" + q.options[q.ans] + "\" and your answer was \"");
                if (map.get(i + 1) - 1 > 3) {
                    System.out.print("at invalid index\"");
                } else {
                    System.out.print(q.options[map.get(i + 1) - 1] + "\"");
                }
                System.out.println();
            }
        }
        System.out.println();

    }

    public static int timeAndAnswer() throws InterruptedException {
        System.out.println(" ");
        Thread t1 = new Thread(() -> {
            try {
                if (sc.hasNextInt()) {
                    a = sc.nextInt();
                }
            } catch (Exception e) {
                a = -1;
            }
        });
        t1.start();
        int i;
        for (i = 10; i >= 0; i--) {
            System.out.print("\rTime left " + i + " " + " Seconds ");
            Thread.sleep(1000);
            if (a != -1) {
                break;
            }
        }
        if (a == -1) {
            a = 4;
        }
        // clearInputBuffer();
        t1.join();
        int val = a;
        a = -1;
        return val;
    }
}
