import java.util.Random;
import java.util.Scanner;

public class EX5 {
    private static final Scanner scanner = new Scanner(System.in);
    private static int correctAnswers = 0;
    private static int wrongAnswers = 0;

    public static void main(String[] args) {
        while (correctAnswers + wrongAnswers < 10) {
            Random random = new Random();
            int a = generateNumber(random);
            int b = generateNumber(random);
            System.out.println("What is the result of " + a + " + " + b + "?");

            Thread userInputThread = new Thread(() -> {
                String userInput = scanner.nextLine().trim();
                if (userInput.isEmpty()) {
                    System.out.println("Time's up! No answer provided.");
                    System.out.println("Test result - Correct: " + correctAnswers + ", Wrong: " + wrongAnswers);
                    System.exit(0); // Terminate the program if no answer is provided within the time limit
                } else if (userInput.matches("[0-9]+")) {
                    int userAnswer = Integer.parseInt(userInput);
                    if (validateAnswer(a, b, userAnswer)) {
                        System.out.println("Correct!");
                        correctAnswers++;
                    } else {
                        System.out.println("Incorrect! The correct answer is: " + (a + b));
                        wrongAnswers++;
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                }
            });

            userInputThread.start();

            try {
                Thread.sleep(2000); // Wait for 2 seconds
                if (userInputThread.isAlive()) {
                    System.out.println("Time's up! No answer provided. The correct answer is: " + (a + b));
                    System.out.println("Test result - Correct: " + correctAnswers + ", Wrong: " + wrongAnswers);
                    System.exit(0); // Terminate the program if no answer is provided within the time limit
                }
                userInputThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Test result - Correct: " + correctAnswers + ", Wrong: " + wrongAnswers);
    }

    public static int generateNumber(Random random) {
        return random.nextInt(9) + 1;
    }

    public static boolean validateAnswer(int a, int b, int userAnswer) {
        return userAnswer == a + b;
    }
}