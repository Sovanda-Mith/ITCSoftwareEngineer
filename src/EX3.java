import java.util.Scanner;

public class EX3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many threads do you want? ");
        int numberOfThreads = Integer.parseInt(scanner.nextLine());

        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            int index = i;
            Runnable runnable = () -> {
                System.out.println(index);
            };
            threads[i] = new Thread(runnable);
        }

        for (Thread thread : threads) {
            try {
                thread.start();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
