import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class PrimeThread extends Thread {
    private int startNum;
    private int endNum;
    private int primeCount;

    public PrimeThread(int startNum, int endNum) {
        this.startNum = startNum;
        this.endNum = endNum;
        this.primeCount = 0;
    }

    @Override
    public void run() {
        List<Integer> primes = new ArrayList<>();
        for (int num = startNum; num <= endNum; num++) {
            if (isPrime(num)) {
                primes.add(num);
                primeCount++;
                System.out.print(Thread.currentThread().getName() + num + " ");
            }
        }
    }

    public int getPrimeCount() {
        return primeCount;
    }

    private boolean isPrime(int num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

}

public class EX4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input start: ");
        int startNum = Integer.parseInt(scanner.nextLine());
        System.out.print("Input end: ");
        int endNum = Integer.parseInt(scanner.nextLine());

        int maxNumberPerThread = 100;
        int range = endNum - startNum + 1;
        int numThreads = (range + maxNumberPerThread - 1) / maxNumberPerThread;

        int step = (range + numThreads - 1) / numThreads;
        int currentStart = startNum;
        int currentEnd = startNum + step - 1;

        List<PrimeThread> threads = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            if (i == numThreads - 1) {
                currentEnd = endNum;
            }

            PrimeThread thread = new PrimeThread(currentStart, currentEnd);
            thread.setName("t" + i + "-");
            threads.add(thread);

            currentStart = currentEnd + 1;
            currentEnd = Math.min(currentStart + step - 1, endNum);
        }

        for (PrimeThread thread : threads) {
            thread.start();
        }

        int totalPrimes = 0;
        for (PrimeThread thread : threads) {
            try {
                thread.join();
                totalPrimes += thread.getPrimeCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nSum of primes = " + totalPrimes);

        scanner.close();

    }
}
