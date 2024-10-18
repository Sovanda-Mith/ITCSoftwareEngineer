import java.util.Scanner;

public class EX3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many threads do you want? ");
        int numberOfThreads = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < numberOfThreads; i++) {
            int index = i;
            Runnable runnable = () -> {
                System.out.println("Index " + index);
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        scanner.close();
    }

}
